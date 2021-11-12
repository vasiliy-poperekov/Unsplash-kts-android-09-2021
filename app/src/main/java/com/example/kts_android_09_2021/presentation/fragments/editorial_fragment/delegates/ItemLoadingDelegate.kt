package com.example.kts_android_09_2021.presentation.fragments.editorial_fragment.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.domain.items.ItemLoading
import com.example.kts_android_09_2021.presentation.fragments.editorial_fragment.view_holders.ItemLoadingViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ItemLoadingDelegate : AbsListItemAdapterDelegate<ItemLoading, Any, ItemLoadingViewHolder>() {
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is ItemLoading
    }

    override fun onCreateViewHolder(parent: ViewGroup): ItemLoadingViewHolder {
        return ItemLoadingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        )
    }

    override fun onBindViewHolder(
        item: ItemLoading,
        holder: ItemLoadingViewHolder,
        payloads: MutableList<Any>
    ) {
    }
}