package com.example.kts_android_09_2021.domain.contracts

object UserContract {
    const val TABLE_NAME = "user"

    object Columns {
        const val ID = "id"
        const val USERNAME = "username"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val PORTFOLIO_URL = "portfolio_url"
        const val LOCATION = "location"
        const val AVATAR_PHOTO = "avatar_photo"
        const val BIO = "bio"
        const val TOTAL_PHOTOS = "total_photos"
        const val TOTAL_LIKES = "total_likes"
    }
}