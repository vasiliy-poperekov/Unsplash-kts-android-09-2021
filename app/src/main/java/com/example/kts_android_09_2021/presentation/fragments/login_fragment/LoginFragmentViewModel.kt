package com.example.kts_android_09_2021.presentation.fragments.login_fragment

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.data.repositories.UserRepository
import com.example.kts_android_09_2021.data.DatastoreRepository
import com.example.kts_android_09_2021.data.AuthRepository
import com.example.kts_android_09_2021.data.api.NetworkingRepository
import com.example.kts_android_09_2021.presentation.utils.NetworkingChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class LoginFragmentViewModel(
    private val authService: AuthorizationService,
    private val datastoreRepository: DatastoreRepository,
    private val networkingChecker: NetworkingChecker,
    private val userRepository: UserRepository,
    private val networkingRepository: NetworkingRepository
) : ViewModel() {

    private val authRepository = AuthRepository()

    private val openAuthMutableState = MutableStateFlow<Intent?>(null)
    private val loadingMutableState = MutableStateFlow(false)
    private val everythingIsReadyMutStateFlow = MutableStateFlow(false)
    private val onAuthCodeFailedMutStateFlow = MutableStateFlow<AuthorizationException?>(null)

    val openAuthObserver: Flow<Intent?>
        get() = openAuthMutableState

    val loadingObserver: Flow<Boolean>
        get() = loadingMutableState

    val everythingIsReadyObserver: Flow<Boolean>
        get() = everythingIsReadyMutStateFlow

    val onAuthCodeFailedObserver: Flow<AuthorizationException?>
        get() = onAuthCodeFailedMutStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.observeTokenChanging().collect { token ->
                if (!token.isNullOrEmpty()) {
                    networkingChecker.observeNetworkChange().collect { isConnected ->
                        if (isConnected) {
                            networkingRepository.getInfoAboutAuthorizedUser()
                                .collect { newUser ->
                                    userRepository.addAuthorizedUser(newUser)
                                    everythingIsReadyMutStateFlow.emit(true)
                                }
                        } else {
                            everythingIsReadyMutStateFlow.emit(true)
                        }
                    }
                }
            }
        }
    }

    fun openLoginPage() {
        val openAuthIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest()
        )

        viewModelScope.launch(Dispatchers.IO) {
            openAuthMutableState.emit(openAuthIntent)
        }
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        authRepository.getAccessToken(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = { token ->
                viewModelScope.launch(Dispatchers.IO) {
                    loadingMutableState.emit(true)
                    datastoreRepository.saveToken(token)
                }
            },
            onError = { exception ->
                viewModelScope.launch(Dispatchers.IO) {
                    loadingMutableState.emit(false)
                    onAuthCodeFailedMutStateFlow.emit(exception)
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}