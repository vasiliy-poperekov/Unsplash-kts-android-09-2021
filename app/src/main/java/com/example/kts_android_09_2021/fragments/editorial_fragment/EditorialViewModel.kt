package com.example.kts_android_09_2021.fragments.editorial_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.db.repositories.EditorialRepository
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.utils.Variables
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditorialViewModel : ViewModel() {

    private var currentJob: Job? = null

    private val networkingRepository = NetworkingRepository()
    private val editorialRepository = EditorialRepository()

    private val savedItemsListMutableState = MutableStateFlow(emptyList<Any?>().toMutableList())
    private val limitMutableState = MutableStateFlow(false)
    private val changingInItemsMutableLiveData = MutableLiveData<Int>()

    private val networkConnectionObserver: Flow<Boolean>
        get() = Variables.isNetworkingConnectionState

    val changingInItemsLiveData: LiveData<Int>
        get() = changingInItemsMutableLiveData

    val savedItemsInfoObserver: Flow<MutableList<Any?>>
        get() = savedItemsListMutableState

    val limitObserver: Flow<Boolean>
        get() = limitMutableState

    init {
        getNewItems(DEFAULT_PAGE_NUMBER)
    }

    fun likePhoto(oldItemEditorial: ItemEditorial) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            networkingRepository.likePhoto(oldItemEditorial)
                .catch { exception ->
                    logExceptions(exception)
                }
                .collect { newItem ->
                    initChangesForFragment(oldItemEditorial, newItem)
                    editorialRepository.updateItem(newItem)
                }
        }
    }

    fun unLikePhoto(oldItemEditorial: ItemEditorial) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            networkingRepository.unLikePhoto(oldItemEditorial)
                .catch { exception ->
                    logExceptions(exception)
                }
                .collect { newItem ->
                    initChangesForFragment(oldItemEditorial, newItem)
                    editorialRepository.updateItem(newItem)
                }
        }
    }

    fun getNewItems(pageNumber: Int) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            var oldItems = savedItemsListMutableState.value

            if (pageNumber == DEFAULT_PAGE_NUMBER) {
                oldItems = emptyList<Any?>().toMutableList()
            }

            oldItems = oldItems.toMutableList().apply {
                if (lastOrNull() is ItemLoading) removeLastOrNull()
            }

            if (pageNumber > 5) {
                limitMutableState.emit(true)
                savedItemsListMutableState.emit(oldItems)
            } else {
                networkConnectionObserver.collect { isConnected ->
                    if (isConnected) {
                        networkingRepository.getPhotosList(pageNumber)
                            .catch { exception ->
                                logExceptions(exception)
                                savedItemsListMutableState.emit(emptyList<Any?>().toMutableList())
                            }
                            .collect { editorialList ->
                                savedItemsListMutableState.emit((oldItems + editorialList + ItemLoading()).toMutableList())
                                editorialRepository.addEditorialItemsList((oldItems as List<ItemEditorial?>) + editorialList)
                            }
                    } else {
                        editorialRepository.getEditorialItemsList()
                            .catch { exception ->
                                logExceptions(exception)
                                savedItemsListMutableState.emit(emptyList<Any?>().toMutableList())
                            }
                            .collect { itemsEditorialList ->
                                savedItemsListMutableState.emit(itemsEditorialList.toMutableList())
                            }
                    }
                }
            }
        }
    }

    private fun copyItemEditorial(
        oldItemEditorial: ItemEditorial,
        newItemEditorial: ItemEditorial
    ): ItemEditorial {
        return oldItemEditorial.apply {
            id = newItemEditorial.id
            userName = newItemEditorial.userName
            avatarUrl = newItemEditorial.avatarUrl
            imageUrl = newItemEditorial.imageUrl
            likes = newItemEditorial.likes
            likedByUser = newItemEditorial.likedByUser
        }
    }

    private fun initChangesForFragment(
        oldItemEditorial: ItemEditorial,
        newItemEditorial: ItemEditorial
    ) {
        val changedItemCopy = copyItemEditorial(oldItemEditorial, newItemEditorial)
        val changedItemIndex = savedItemsListMutableState.value.indexOf(oldItemEditorial)
        savedItemsListMutableState.value[changedItemIndex] = changedItemCopy
        changingInItemsMutableLiveData.postValue(changedItemIndex)
    }

    companion object {
        const val DEFAULT_PAGE_NUMBER = 1
    }
}