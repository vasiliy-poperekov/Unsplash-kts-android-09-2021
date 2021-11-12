package com.example.kts_android_09_2021.domain.contracts

object LikedPhotosContract {
    const val TABLE_NAME = "liked_photos"

    object Columns {
        const val ID = "id"
        const val USERNAME = "username"
        const val AVATAR_URL = "avatar_url"
        const val IMAGE_URL = "image_url"
    }
}