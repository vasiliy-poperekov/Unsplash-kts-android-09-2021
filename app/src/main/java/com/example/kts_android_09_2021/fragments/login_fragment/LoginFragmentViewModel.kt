package com.example.kts_android_09_2021.fragments.login_fragment

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kts_android_09_2021.network.auth.AuthRepository
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val authService = AuthorizationService(getApplication())
    private val openAuthMutableLiveData = MutableLiveData<Intent>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val authSuccessMutableLiveData = MutableLiveData(false)

    val openAuthLiveData: LiveData<Intent>
        get() = openAuthMutableLiveData

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    val authSuccessLiveData: LiveData<Boolean>
        get() = authSuccessMutableLiveData

    fun openLoginPage() {

        val openAuthIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest()
        )

        openAuthMutableLiveData.postValue(openAuthIntent)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        authRepository.getAccessToken(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                loadingMutableLiveData.postValue(true)
                authSuccessMutableLiveData.postValue(true)
            },
            onError = {
                loadingMutableLiveData.postValue(false)
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