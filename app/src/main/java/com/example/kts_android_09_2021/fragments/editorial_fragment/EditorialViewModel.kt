package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.network.data.NetworkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class EditorialViewModel : ViewModel() {

    private var currentJob: Job? = null

    private val savedItemsListMutableLiveData = MutableLiveData<MutableList<Any?>>()
    private val changingInItemsMutableLiveData = MutableLiveData<List<Int>>()
    private val limitMutableLiveData = MutableLiveData<Boolean>()

    private val networkingRepository = NetworkingRepository()

    val changingInItemsLiveData: LiveData<List<Int>>
        get() = changingInItemsMutableLiveData

    val savedItemsInfoLiveData: LiveData<MutableList<Any?>>
        get() = savedItemsListMutableLiveData

    val limitLiveData: LiveData<Boolean>
        get() = limitMutableLiveData

    init {
        getNewItems(1)
    }

    fun likePhoto(itemEditorial: ItemEditorial) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                networkingRepository.likePhoto(itemEditorial)
            }.onSuccess {
                val changedItemIndex = savedItemsInfoLiveData.value?.indexOf(itemEditorial)!!
                savedItemsInfoLiveData.value?.set(changedItemIndex, it)
                changingInItemsMutableLiveData.postValue(listOf(changedItemIndex, it.likes))
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun getNewItems(pageNumber: Int) {

        var newItems = savedItemsListMutableLiveData.value ?: emptyList<Any?>().toMutableList()

        newItems = newItems.toMutableList().apply {
            if (lastOrNull() is ItemLoading) removeLastOrNull()
        }

        if (pageNumber > 5) {
            limitMutableLiveData.postValue(true)
            savedItemsListMutableLiveData.postValue(newItems)
            return
        }

        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                networkingRepository.getPhotosList(pageNumber)
            }.onSuccess {
                savedItemsListMutableLiveData.postValue((newItems + it + ItemLoading()).toMutableList())
            }.onFailure {
                Timber.e(it)
                savedItemsListMutableLiveData.postValue(emptyList<Any?>().toMutableList())
            }

            Log.e("Pagination", newItems.size.toString())
        }
    }
}