package com.example.kts_android_09_2021.presentation.fragments.main_fragment

import androidx.lifecycle.ViewModel
import com.example.kts_android_09_2021.presentation.utils.NetworkingChecker
import kotlinx.coroutines.flow.Flow

class MainFragmentViewModel(
    networkingChecker: NetworkingChecker
) : ViewModel() {
    val networkingObserver: Flow<Boolean> = networkingChecker.observeNetworkChange()
}