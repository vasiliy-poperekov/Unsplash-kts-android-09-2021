package com.example.kts_android_09_2021.utils

import androidx.room.TypeConverter
import com.example.kts_android_09_2021.network.entities.profile.AvatarPhoto

class AvatarPhotoConverter {
    @TypeConverter
    fun fromAvatarPhoto(avatarPhoto: AvatarPhoto?): String {
        return if (avatarPhoto == null) "null"
        else avatarPhoto.large + "," + avatarPhoto.medium + "," + avatarPhoto.small
    }

    @TypeConverter
    fun toAvatarPhoto(data: String): AvatarPhoto? {
        return if (data == "null") null
        else {
            AvatarPhoto(
                large = data.split(",")[0],
                medium = data.split(",")[1],
                small = data.split(",")[2]
            )
        }
    }
}