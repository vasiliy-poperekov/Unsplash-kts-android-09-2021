package com.example.kts_android_09_2021.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val pasteNewItems: () -> Unit,
    private val visibilityThreshold: Int = 0
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) =
        with(layoutManager) {
            if (dy <= 0) return@with

            val scrolledOffItems = findFirstVisibleItemPosition()
            val visibleItems = childCount
            val itemsTotal = itemCount

            if (visibleItems + scrolledOffItems + visibilityThreshold >= itemsTotal) {
                pasteNewItems.invoke()
            }
        }
}