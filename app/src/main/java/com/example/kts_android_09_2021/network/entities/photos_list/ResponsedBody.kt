package com.example.kts_android_09_2021.network.entities.photos_list

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponsedBody(
    val photo: Photo
)