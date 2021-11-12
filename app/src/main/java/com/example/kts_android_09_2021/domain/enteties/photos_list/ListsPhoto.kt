package com.example.kts_android_09_2021.domain.enteties.photos_list

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListsPhoto(
    val id: String,
    val urls: Urls,
    val user: UserInfoFromPhoto,
    val likes: Int,
    @Json(name = "liked_by_user") val likedByUser: Boolean
)