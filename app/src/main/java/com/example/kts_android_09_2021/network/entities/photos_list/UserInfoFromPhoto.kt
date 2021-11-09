package com.example.kts_android_09_2021.network.entities.photos_list

import com.example.kts_android_09_2021.network.entities.profile.AvatarPhoto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoFromPhoto(
    @Json(name = "username") val userName: String,
    @Json(name = "profile_image") val avatarPhoto: AvatarPhoto
)