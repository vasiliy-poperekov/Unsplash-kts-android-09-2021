package com.example.kts_android_09_2021.fragments.editorial_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.db.repositories_impl.EditorialRepositoryImpl
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.utils.NetworkingChecker
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditorialViewModel(
    private val networkingRepository: NetworkingRepository,
    private val editorialRepository: EditorialRepositoryImpl,
    networkingChecker: NetworkingChecker
) : ViewModel() {
    private var pageNumber = 1
    private var mainJob: Job? = null
    private var loadJob: Job? = null

    private val savedItemsListMutableState = MutableStateFlow<MutableList<Any?>?>(null)
    private val errorsMutableStateFlow = MutableStateFlow<Throwable?>(null)
    private val changingInItemsMutSharedFlow = MutableSharedFlow<Int>(0)

    private val networkConnectionObserver: Flow<Boolean> =
        networkingChecker.observeNetworkChange()

    val errorsObserver: Flow<Throwable?>
        get() = errorsMutableStateFlow

    val savedItemsInfoObserver: Flow<List<Any?>?>
        get() = savedItemsListMutableState

    val changingInItemsObserver: SharedFlow<Int>
        get() = changingInItemsMutSharedFlow

    init {
        getNewItems {}
    }

    fun refreshList() {
        pageNumber = 1
        savedItemsListMutableState.value?.clear()
        getNewItems { }
    }

    fun likePhoto(oldItemEditorial: ItemEditorial) {
        viewModelScope.launch {
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
        viewModelScope.launch {
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

    fun getNewItems(load: () -> Unit) {
        mainJob?.cancel()
        mainJob = viewModelScope.launch {
            var oldItems = savedItemsListMutableState.value ?: emptyList()

            oldItems = oldItems.toMutableList().apply {
                if (lastOrNull() is ItemLoading) removeLastOrNull()
            }
            networkConnectionObserver.collect { isConnected ->
                loadJob?.cancel()
                if (isConnected) {
                    loadJob = launch {
                        networkingRepository.getPhotosList(pageNumber)
                            .catch { exception ->
                                logExceptions(exception)
                                errorsMutableStateFlow.emit(exception)
                                savedItemsListMutableState.emit(emptyList<Any?>().toMutableList())
                            }
                            .collect { editorialList ->
                                savedItemsListMutableState.emit((oldItems + editorialList + ItemLoading).toMutableList())
                                editorialRepository.addEditorialItemsList((oldItems as List<ItemEditorial?>) + editorialList)
                                pageNumber++
                                load()
                            }
                    }
                } else {
                    loadJob = launch {
                        editorialRepository.getEditorialItemsList()
                            .catch { exception ->
                                errorsMutableStateFlow.emit(exception)
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

    private fun setItemChanges(
        oldItemEditorial: ItemEditorial,
        newItemEditorial: ItemEditorial
    ): ItemEditorial {
        return oldItemEditorial
            .apply {
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
        val changedItemCopy = setItemChanges(oldItemEditorial, newItemEditorial)
        val changedItemIndex = savedItemsListMutableState.value!!.indexOf(oldItemEditorial)
        savedItemsListMutableState.value!![changedItemIndex] = changedItemCopy
        viewModelScope.launch(Dispatchers.IO) {
            changingInItemsMutSharedFlow.emit(changedItemIndex)
        }
    }
}