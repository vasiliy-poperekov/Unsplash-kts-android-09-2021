package com.example.kts_android_09_2021.fragments.login_fragment

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.MainApplication
import com.example.kts_android_09_2021.network.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val authService = AuthorizationService(getApplication())
    private val datastoreRepository = (getApplication() as MainApplication).datastoreRepository

    private val openAuthMutableState = MutableStateFlow<Intent?>(null)
    private val loadingMutableState = MutableStateFlow(false)

    val openAuthObserver: Flow<Intent?>
        get() = openAuthMutableState

    val loadingObserver: Flow<Boolean>
        get() = loadingMutableState

    val tokenObserver: Flow<String?>
        get() = datastoreRepository.observeTokenChanging()

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
            onComplete = {
                viewModelScope.launch(Dispatchers.IO) {
                    loadingMutableState.emit(true)
                    datastoreRepository.saveToken(it)
                }
            },
            onError = {
                viewModelScope.launch(Dispatchers.IO) {
                    loadingMutableState.emit(false)
                }
                onAuthCodeFailed(it)
            }
        )
    }

    fun onAuthCodeFailed(exception: AuthorizationException) {
        Toast.makeText(getApplication(), exception.toJsonString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}