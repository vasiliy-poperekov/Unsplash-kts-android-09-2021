package com.example.kts_android_09_2021.fragments.editorial_fragment.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kts_android_09_2021.databinding.ItemEditorialBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders.ItemEditorialViewHolder
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ItemEditorialDelegate(
    private val likePhoto: (ItemEditorial) -> Unit
) :
    AbsListItemAdapterDelegate<ItemEditorial, Any, ItemEditorialViewHolder>() {
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is ItemEditorial
    }

    override fun onCreateViewHolder(parent: ViewGroup): ItemEditorialViewHolder {
        return ItemEditorialViewHolder(
            ItemEditorialBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ) { likePhoto(it) }
    }

    override fun onBindViewHolder(
        item: ItemEditorial,
        holder: ItemEditorialViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}