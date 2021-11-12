package com.example.kts_android_09_2021.presentation.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val pasteNewItems: (() -> Unit) -> Unit,
    private val visibilityThreshold: Int = 0
) : RecyclerView.OnScrollListener() {
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        with(layoutManager) {
            if (dy <= 0) return@with

            val scrolledOffItems = findFirstVisibleItemPosition()
            val visibleItems = childCount
            val itemsTotal = itemCount

            if (isLoading && visibleItems + scrolledOffItems + visibilityThreshold >= itemsTotal) {
                pasteNewItems { isLoading = true }
                isLoading = false
            }
        }

    }
}