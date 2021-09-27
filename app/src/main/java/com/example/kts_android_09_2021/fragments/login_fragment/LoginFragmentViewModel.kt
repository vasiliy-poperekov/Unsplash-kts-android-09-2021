package com.example.kts_android_09_2021.fragments.login_fragment

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginFragmentViewModel : ViewModel() {
    var emailString: String = ""
    var passwordString: String = ""
    var stateFieldsLiveData = MutableLiveData(
        StateFields(
            emailIsCorrect = false,
            passwordIsCorrect = false
        )
    )

    fun changeEmail(email: String) {
        stateFieldsLiveData.postValue(stateFieldsLiveData.value?.apply {
            emailIsCorrect = checkEmail(email)
        })
        emailString = email
    }

    fun changePassword(password: String) {
        stateFieldsLiveData.postValue(stateFieldsLiveData.value?.apply {
            passwordIsCorrect = checkPassword(password)
        })
        passwordString = password
    }

    private fun checkEmail(emailString: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(emailString).matches()

    private fun checkPassword(password: String): Boolean =
        password.length >= 8
}