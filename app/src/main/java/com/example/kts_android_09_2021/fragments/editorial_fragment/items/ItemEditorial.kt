package com.example.kts_android_09_2021.fragments.editorial_fragment.items

data class ItemEditorial(
    val id: String,
    val userName: String,
    val avatarUrl: String,
    val imageUrl: String,
    val likes: Int,
    var likedByUser: Boolean
)