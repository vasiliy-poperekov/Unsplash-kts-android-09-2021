package com.example.kts_android_09_2021.fragments.login_fragment

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginFragmentViewModel : ViewModel() {
    var emailStringLiveData: String = ""
    var passwordStringLiveData: String = ""
    val emailIsCorrectLiveData = MutableLiveData(false)
    val passwordIsCorrectLiveData = MutableLiveData(false)

    fun changeEmail(email: String) {
        emailIsCorrectLiveData.postValue(checkEmail(email))
        emailStringLiveData = email
    }

    fun changePassword(password: String) {
        passwordIsCorrectLiveData.postValue(checkPassword(password))
        passwordStringLiveData = password
    }

    private fun checkEmail(emailString: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(emailString).matches()

    private fun checkPassword(password: String): Boolean =
        password.length >= 8
}