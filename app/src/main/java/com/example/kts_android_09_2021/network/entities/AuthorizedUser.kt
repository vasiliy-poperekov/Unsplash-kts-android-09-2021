package com.example.kts_android_09_2021.network.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorizedUser(
    var id: String,
    var username: String,
    @Json(name = "first_name") var firstName: String,
    @Json(name = "last_name") var lastName: String,
    @Json(name = "portfolio_url") var portfolioUrl: String?,
    @Json(name = "location") var location: String?,
    @Json(name = "profile_image") var profileImage: ProfileImage?
)