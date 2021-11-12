package com.example.kts_android_09_2021.data.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.kts_android_09_2021.domain.contracts.UserContract
import com.example.kts_android_09_2021.domain.enteties.profile.AvatarPhoto
import com.example.kts_android_09_2021.data.AvatarPhotoConverter

@Entity(tableName = UserContract.TABLE_NAME)
@TypeConverters(AvatarPhotoConverter::class)
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = UserContract.Columns.ID)
    val id: String,

    @ColumnInfo(name = UserContract.Columns.USERNAME)
    val username: String,

    @ColumnInfo(name = UserContract.Columns.FIRST_NAME)
    val firstName: String,

    @ColumnInfo(name = UserContract.Columns.LAST_NAME)
    val lastName: String,

    @ColumnInfo(name = UserContract.Columns.PORTFOLIO_URL)
    val portfolioUrl: String?,

    @ColumnInfo(name = UserContract.Columns.LOCATION)
    val location: String?,

    @ColumnInfo(name = UserContract.Columns.AVATAR_PHOTO)
    val avatarPhoto: AvatarPhoto?,

    @ColumnInfo(name = UserContract.Columns.BIO)
    val bio: String?,

    @ColumnInfo(name = UserContract.Columns.TOTAL_PHOTOS)
    val totalPhotos: Int,

    @ColumnInfo(name = UserContract.Columns.TOTAL_LIKES)
    val totalLikes: Int,
)