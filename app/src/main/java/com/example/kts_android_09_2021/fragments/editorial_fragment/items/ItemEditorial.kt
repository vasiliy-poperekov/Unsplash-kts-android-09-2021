package com.example.kts_android_09_2021.fragments.editorial_fragment.items

data class ItemEditorial(
    var id: String,
    var userName: String,
    var avatarUrl: String,
    var imageUrl: String,
    var likes: Int,
    var likedByUser: Boolean
)