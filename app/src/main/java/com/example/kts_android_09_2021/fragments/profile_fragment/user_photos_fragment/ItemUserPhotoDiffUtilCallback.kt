package com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ItemUserPhotoDiffUtilCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.javaClass == newItem.javaClass && when (oldItem) {
            is ItemUserPhoto -> oldItem.id == (newItem as ItemUserPhoto).id
            else -> true
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
}