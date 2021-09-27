package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithImage
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithoutImage
import kotlin.random.Random

class EditorialViewModel : ViewModel() {
    val savedItemsInfoLiveData = MutableLiveData(listOf<Any>())

    init {
        loadItemsInRecycler()
    }

    fun loadItemsInRecycler() {
        val newItems = savedItemsInfoLiveData.value!!.toMutableList().apply {
            if (lastOrNull() is ItemLoading) removeLastOrNull()
        } + getNewItems() + ItemLoading()
        savedItemsInfoLiveData.postValue(newItems)
        Log.e("Pagination", newItems.size.toString())
    }

    private fun getNewItems() = List(20) {
        when ((1..2).random()) {
            1 -> ItemWithImage(
                Random.nextInt(),
                "Simple title",
                "Somebody"
            )
            2 -> ItemWithoutImage(
                Random.nextInt(),
                "Simple title",
                "Somebody",
                (0..100).random()
            )
            else -> error("Wrong random number")
        }
    }
}