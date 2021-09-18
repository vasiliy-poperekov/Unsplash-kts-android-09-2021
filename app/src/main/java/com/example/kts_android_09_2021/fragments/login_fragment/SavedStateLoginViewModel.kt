package com.example.kts_android_09_2021.fragments.login_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SavedStateLoginViewModel(
    private val state: SavedStateHandle
) : ViewModel() {
    val liveData = state.getLiveData("liveData", "")

    fun saveState() {
        state.set("liveData", liveData.value)
    }
}