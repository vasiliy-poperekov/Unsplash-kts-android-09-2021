package com.example.kts_android_09_2021.fragments.profile_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import com.example.kts_android_09_2021.network.entities.AuthorizedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragmentViewModel : ViewModel() {

    private val networkingRepository = NetworkingRepository()
    private val currentUserMutableLiveData = MutableLiveData<AuthorizedUser>()

    val currentUserLiveData: LiveData<AuthorizedUser>
        get() = currentUserMutableLiveData

    init {
        getAuthorizedUser()
    }

    private fun getAuthorizedUser() {
        viewModelScope.launch(Dispatchers.IO) {
            currentUserMutableLiveData.postValue(networkingRepository.getInfoAboutAuthorizedUser())
        }
    }
}