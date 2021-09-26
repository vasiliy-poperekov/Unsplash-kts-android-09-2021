package com.example.kts_android_09_2021.fragments.editorial_fragment.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kts_android_09_2021.databinding.ItemEditorialTestWithoutImageBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithoutImage
import com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders.ItemWithoutImageViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ItemWithoutImageDelegate :
    AbsListItemAdapterDelegate<Any, Any, ItemWithoutImageViewHolder>() {
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean =
        item is ItemWithoutImage

    override fun onCreateViewHolder(parent: ViewGroup): ItemWithoutImageViewHolder {
        return ItemWithoutImageViewHolder(
            ItemEditorialTestWithoutImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        item: Any,
        holder: ItemWithoutImageViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item as ItemWithoutImage)
    }
}