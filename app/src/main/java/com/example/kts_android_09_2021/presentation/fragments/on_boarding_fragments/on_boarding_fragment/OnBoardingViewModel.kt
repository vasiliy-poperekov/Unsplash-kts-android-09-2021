package com.example.kts_android_09_2021.presentation.fragments.on_boarding_fragments.on_boarding_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.domain.items.ScreenItem
import com.example.kts_android_09_2021.data.DatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {

    private val savedItemsMutStateFlow = MutableStateFlow<List<ScreenItem>?>(null)

    val enterObserver: Flow<Boolean?>
        get() = datastoreRepository.observeEnterChanging()

    val savedItemsObserver: Flow<List<ScreenItem>?>
        get() = savedItemsMutStateFlow

    fun enterWasDone() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.saveEnterFlag(true)
        }
    }

    fun saveItems(itemsList: List<ScreenItem>){
        viewModelScope.launch(Dispatchers.IO) {
            savedItemsMutStateFlow.emit(itemsList)
        }
    }
}