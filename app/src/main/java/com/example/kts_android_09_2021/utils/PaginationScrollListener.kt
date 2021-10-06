package com.example.kts_android_09_2021.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val pasteNewItems: (Int) -> Unit,
    private val visibilityThreshold: Int = 0
) : RecyclerView.OnScrollListener() {
    var isLoading = true
    var isLastPage = false
    private var pageCounter = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) =
        with(layoutManager) {
            if (dy <= 0) return@with

            val scrolledOffItems = findFirstVisibleItemPosition()
            val visibleItems = childCount
            val itemsTotal = itemCount

            if (!isLastPage && isLoading && visibleItems + scrolledOffItems + visibilityThreshold >= itemsTotal) {
                pasteNewItems.invoke(++pageCounter)
                isLoading = false
            }
        }
}