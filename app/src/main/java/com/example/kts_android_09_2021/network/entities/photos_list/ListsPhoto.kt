package com.example.kts_android_09_2021.network.entities.photos_list

import com.example.kts_android_09_2021.network.entities.Photo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListsPhoto(
    override val id: String,
    override val urls: Urls,
    val user: UserInfoFromPhoto,
    val likes: Int,
    @Json(name = "liked_by_user") val likedByUser: Boolean
): Photo(id, urls)
