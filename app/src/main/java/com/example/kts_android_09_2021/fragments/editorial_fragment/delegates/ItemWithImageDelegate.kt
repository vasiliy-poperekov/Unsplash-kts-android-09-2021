package com.example.kts_android_09_2021.fragments.editorial_fragment.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kts_android_09_2021.databinding.ItemEditorialTestWithImageBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithImage
import com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders.ItemWithImageViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ItemWithImageDelegate : AbsListItemAdapterDelegate<Any, Any, ItemWithImageViewHolder>() {
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean =
        item is ItemWithImage

    override fun onCreateViewHolder(parent: ViewGroup): ItemWithImageViewHolder {
        return ItemWithImageViewHolder(
            ItemEditorialTestWithImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: Any,
        holder: ItemWithImageViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item as ItemWithImage)
    }
}