package com.example.kts_android_09_2021.presentation.fragments.profile_fragment.user_photos_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kts_android_09_2021.databinding.ItemProfilePhotoBinding
import com.example.kts_android_09_2021.domain.items.ItemUserPhoto
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ItemUserPhotosDelegate :
    AbsListItemAdapterDelegate<ItemUserPhoto, Any, ItemUserPhotosViewHolder>() {
    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean =
        item is ItemUserPhoto

    override fun onCreateViewHolder(parent: ViewGroup): ItemUserPhotosViewHolder {
        return ItemUserPhotosViewHolder(
            ItemProfilePhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        item: ItemUserPhoto,
        holder: ItemUserPhotosViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}