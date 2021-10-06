package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial

class DiffUtilItemsCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.javaClass == newItem.javaClass && when (oldItem) {
            is ItemEditorial -> oldItem.id == (newItem as ItemEditorial).id && oldItem.likedByUser == newItem.likedByUser
            else -> true
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
}