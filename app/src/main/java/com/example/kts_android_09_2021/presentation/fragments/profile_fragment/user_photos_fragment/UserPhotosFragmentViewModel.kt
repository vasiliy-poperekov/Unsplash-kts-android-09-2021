package com.example.kts_android_09_2021.presentation.fragments.profile_fragment.user_photos_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.data.repositories.UserPhotosRepository
import com.example.kts_android_09_2021.data.repositories.UserRepository
import com.example.kts_android_09_2021.domain.items.ItemLoading
import com.example.kts_android_09_2021.domain.items.ItemUserPhoto
import com.example.kts_android_09_2021.data.api.NetworkingRepository
import com.example.kts_android_09_2021.presentation.utils.NetworkingChecker
import com.example.kts_android_09_2021.domain.common.logExceptions
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.abs

class UserPhotosFragmentViewModel(
    private val userRepository: UserRepository,
    private val networkingRepository: NetworkingRepository,
    private val userPhotosRepository: UserPhotosRepository,
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

            if (oldItems.size < currentUser.totalPhotos) {
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
                                    if ((pageNumber + 1) * 10 > currentUser.totalLikes)
                                        itemsOnPage =
                                            abs(pageNumber * 10 - currentUser.totalLikes)
                                    pageNumber++
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