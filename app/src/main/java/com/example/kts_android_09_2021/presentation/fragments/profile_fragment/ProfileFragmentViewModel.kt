package com.example.kts_android_09_2021.presentation.fragments.profile_fragment

import androidx.lifecycle.*
import com.example.kts_android_09_2021.data.DatastoreRepository
import com.example.kts_android_09_2021.data.api.NetworkingRepository
import com.example.kts_android_09_2021.data.repositories.EditorialRepository
import com.example.kts_android_09_2021.data.repositories.LikedPhotosRepository
import com.example.kts_android_09_2021.data.repositories.UserPhotosRepository
import com.example.kts_android_09_2021.data.repositories.UserRepository
import com.example.kts_android_09_2021.domain.enteties.profile.AuthorizedUser
import com.example.kts_android_09_2021.presentation.utils.NetworkingChecker
import com.example.kts_android_09_2021.domain.common.logExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    private val networkingRepository: NetworkingRepository,
    private val datastoreRepository: DatastoreRepository,
    private val editorialRepository: EditorialRepository,
    private val userRepository: UserRepository,
    private val likedPhotosRepository: LikedPhotosRepository,
    private val userPhotosRepository: UserPhotosRepository,
    networkingChecker: NetworkingChecker
) : ViewModel() {
    private var mainJob: Job? = null
    private var loadJob: Job? = null

    private val currentUserMutableState = MutableStateFlow<AuthorizedUser?>(null)

    val currentUserObserver: Flow<AuthorizedUser?>
        get() = currentUserMutableState

    val connectionObserver: Flow<Boolean> =
        networkingChecker.observeNetworkChange()

    val tokenObserver: Flow<String?>
        get() = datastoreRepository.observeTokenChanging()

    init {
        getAuthorizedUser()
    }

    private fun getAuthorizedUser() {
        mainJob?.cancel()
        mainJob = viewModelScope.launch(Dispatchers.IO) {
            connectionObserver
                .collect { isConnected ->
                    loadJob?.cancel()
                    if (isConnected) {
                        loadJob = launch {
                            networkingRepository.getInfoAboutAuthorizedUser()
                                .catch { logExceptions(it) }
                                .collect { authorizedUser ->
                                    currentUserMutableState.emit(authorizedUser)
                                    userRepository.addAuthorizedUser(authorizedUser)
                                }
                        }
                    } else {
                        loadJob = launch {
                            currentUserMutableState.emit(userRepository.getAuthorizedUser())
                        }
                    }
                }
        }
    }

    fun logOut() {
        mainJob?.cancel()
        mainJob = viewModelScope.launch(Dispatchers.IO) {
            editorialRepository.deleteAllItems()
            userRepository.deleteAuthorizedUser()
            likedPhotosRepository.deleteAllItems()
            userPhotosRepository.deleteAllItems()
            datastoreRepository.saveToken("")
        }
    }
}