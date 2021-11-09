package com.example.kts_android_09_2021.network.entities.profile

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorizedUser(
    val id: String,
    val username: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "portfolio_url") val portfolioUrl: String?,
    @Json(name = "location") val location: String?,
    @Json(name = "profile_image") val avatarPhoto: AvatarPhoto?,
    val bio: String?,
    @Json(name = "total_photos") val totalPhotos: Int,
    @Json(name = "total_likes") val totalLikes: Int
)