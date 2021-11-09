package com.example.kts_android_09_2021.network.entities.profile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvatarPhoto(
    var small: String,
    var medium: String,
    var large: String
)