package com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.db.repositories_impl.UserPhotosRepositoryImpl
import com.example.kts_android_09_2021.db.repositories_impl.UserRepositoryImpl
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.utils.NetworkingChecker
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserPhotosFragmentViewModel(
    private val userRepository: UserRepositoryImpl,
    private val networkingRepository: NetworkingRepository,
    private val userPhotosRepository: UserPhotosRepositoryImpl,
    private val networkingChecker: NetworkingChecker
) : ViewModel() {
    private var pageNumber = START_PAGE_NUMBER
    private var itemsOnPage = DEFAULT_ITEMS_ON_PAGE
    private var currentJob: Job? = null
    private var loadJob: Job? = null

    private val photosListMutState = MutableStateFlow<List<Any>?>(null)
    private val errorsMutableStateFlow = MutableStateFlow<Throwable?>(null)

    val errorsObserver: Flow<Throwable?>
        get() = errorsMutableStateFlow

    val photosListObserver: Flow<List<Any>?>
        get() = photosListMutState

    init {
        getPhotosList {}
    }

    fun refreshPhotosList() {
        pageNumber = START_PAGE_NUMBER
        itemsOnPage = DEFAULT_ITEMS_ON_PAGE
        photosListMutState.value = null
        getPhotosList { }
    }

    fun getPhotosList(load: () -> Unit) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val currentUser = userRepository.getAuthorizedUser()
            var oldItems = photosListMutState.value ?: emptyList()

            oldItems = oldItems.toMutableList().apply {
                if (lastOrNull() is ItemLoading) removeLastOrNull()
            }.toList()

            if (oldItems.size < currentUser.totalLikes) {
                networkingChecker.observeNetworkChange().collect { isConnected ->
                    loadJob?.cancel()
                    if (isConnected) {
                        loadJob = launch {
                            networkingRepository.getUsersPhotos(
                                currentUser.username,
                                pageNumber,
                                itemsOnPage
                            )
                                .catch { exception ->
                                    logExceptions(exception)
                                    photosListMutState.emit(emptyList())
                                    errorsMutableStateFlow.emit(exception)
                                }
                                .collect { userPhotosList ->
                                    photosListMutState.emit(oldItems + userPhotosList + ItemLoading)
                                    userPhotosRepository.addUserPhotosList((oldItems + userPhotosList) as List<ItemUserPhoto?>)
                                    load()
                                }
                        }
                    } else {
                        loadJob = launch {
                            userPhotosRepository.getUserPhotosList()
                                .catch { exception ->
                                    logExceptions(exception)
                                    photosListMutState.emit(emptyList())
                                    errorsMutableStateFlow.emit(exception)
                                }
                                .collect { userPhotosList ->
                                    photosListMutState.emit(userPhotosList)
                                }
                        }
                    }
                }
            } else {
                photosListMutState.emit(oldItems)
            }
        }
    }

    companion object {
        const val START_PAGE_NUMBER = 1
        const val DEFAULT_ITEMS_ON_PAGE = 10
    }
}