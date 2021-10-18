package com.example.kts_android_09_2021.fragments.on_boarding_fragments.on_boarding_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {
    private val datastoreRepository = (getApplication() as MainApplication).datastoreRepository

    val enterObserver: Flow<Boolean?>
        get() = datastoreRepository.observeEnterChanging()

    fun enterWasDone() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.saveEnterFlag(true)
        }
    }
}