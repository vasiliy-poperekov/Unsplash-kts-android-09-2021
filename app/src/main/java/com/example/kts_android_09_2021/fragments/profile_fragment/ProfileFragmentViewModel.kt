package com.example.kts_android_09_2021.fragments.profile_fragment

import android.app.Application
import androidx.lifecycle.*
import com.example.kts_android_09_2021.MainApplication
import com.example.kts_android_09_2021.db.repositories.EditorialRepository
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.network.entities.AuthorizedUser
import com.example.kts_android_09_2021.utils.Variables
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(
    application: Application
) : AndroidViewModel(application) {
    private var currentJob: Job? = null

    private val networkingRepository = NetworkingRepository()
    private val datastoreRepository = (getApplication<MainApplication>().datastoreRepository)
    private val editorialRepository = EditorialRepository()

    private val currentUserMutableState = MutableStateFlow<AuthorizedUser?>(null)

    val currentUserObserver: Flow<AuthorizedUser?>
        get() = currentUserMutableState

    val connectionObserver: Flow<Boolean>
        get() = Variables.isNetworkingConnectionState

    val tokenObserver: Flow<String?>
        get() = datastoreRepository.observeTokenChanging()

    init {
        getAuthorizedUser()
    }

    private fun getAuthorizedUser() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            connectionObserver
                .catch { logExceptions(it) }
                .collect { isConnected ->
                    if (isConnected) {
                        networkingRepository.getInfoAboutAuthorizedUser()
                            .catch { logExceptions(it) }
                            .collect { authorizedUser ->
                                currentUserMutableState.emit(authorizedUser)
                            }
                    }
                }
        }
    }

    fun logOut() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            editorialRepository.deleteAllItems()
            datastoreRepository.saveToken("")
        }
    }
}