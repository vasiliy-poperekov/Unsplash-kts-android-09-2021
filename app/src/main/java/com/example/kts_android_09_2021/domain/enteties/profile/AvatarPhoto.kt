package com.example.kts_android_09_2021.domain.enteties.profile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvatarPhoto(
    var small: String,
    var medium: String,
    var large: String
)