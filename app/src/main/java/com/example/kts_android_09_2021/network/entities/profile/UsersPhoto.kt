package com.example.kts_android_09_2021.network.entities.profile

import com.example.kts_android_09_2021.network.entities.Photo
import com.example.kts_android_09_2021.network.entities.photos_list.Urls
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersPhoto(
    override val id: String,
    override val urls: Urls
): Photo(id, urls)