package com.example.kts_android_09_2021.domain.enteties.profile

import com.example.kts_android_09_2021.domain.enteties.photos_list.Urls
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersPhoto(
    val id: String,
    val urls: Urls
)