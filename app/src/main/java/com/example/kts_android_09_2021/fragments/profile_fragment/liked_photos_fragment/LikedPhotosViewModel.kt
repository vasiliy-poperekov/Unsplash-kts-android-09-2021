package com.example.kts_android_09_2021.fragments.profile_fragment.liked_photos_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.db.repositories_impl.LikedPhotosRepositoryImpl
import com.example.kts_android_09_2021.db.repositories_impl.UserRepositoryImpl
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment.ItemUserPhoto
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.utils.NetworkingChecker
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.abs

class LikedPhotosViewModel(
    private val networkingRepository: NetworkingRepository,
    private val userRepository: UserRepositoryImpl,
    private val likedPhotosRepository: LikedPhotosRepositoryImpl,
    private val networkingChecker: NetworkingChecker
) : ViewModel() {
    private var pageNumber = START_PAGE_NUMBER
    private var itemsOnPage = DEFAULT_ITEMS_ON_PAGE
    private var mainJob: Job? = null
    private var loadJob: Job? = null

    private val photosListMutState = MutableStateFlow<List<Any>?>(null)
    private val errorMutState = MutableStateFlow<Throwable?>(null)

    val photosListObserver: Flow<List<Any>?>
        get() = photosListMutState

    val errorObserver: Flow<Throwable?>
        get() = errorMutState

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
        mainJob?.cancel()
        mainJob = viewModelScope.launch {
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
                            networkingRepository.getLikedPhotos(
                                currentUser.username,
                                pageNumber,
                                itemsOnPage
                            )
                                .catch { exception ->
                                    errorMutState.emit(exception)
                                    logExceptions(exception)
                                    photosListMutState.emit(emptyList())
                                }
                                .collect { likedPhotosList ->
                                    if ((pageNumber + 1) * 10 > currentUser.totalLikes)
                                        itemsOnPage =
                                            abs(pageNumber * 10 - currentUser.totalLikes)
                                    pageNumber++
                                    photosListMutState.emit(oldItems + likedPhotosList + ItemLoading)
                                    likedPhotosRepository.addLikedPhotosList((oldItems + likedPhotosList) as List<ItemUserPhoto?>)
                                    load()
                                }
                        }
                    } else {
                        loadJob = launch {
                            likedPhotosRepository.getLikedPhotosList()
                                .catch { exception ->
                                    errorMutState.emit(exception)
                                    logExceptions(exception)
                                    photosListMutState.emit(emptyList())
                                }
                                .collect { likedPhotosList ->
                                    photosListMutState.emit(likedPhotosList)
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