package com.example.kts_android_09_2021.fragments.main_fragment

import androidx.lifecycle.ViewModel
import com.example.kts_android_09_2021.utils.Variables
import kotlinx.coroutines.flow.Flow

class MainFragmentViewModel : ViewModel() {
    val networkingObserver: Flow<Boolean>
        get() = Variables.isNetworkingConnectionState
}