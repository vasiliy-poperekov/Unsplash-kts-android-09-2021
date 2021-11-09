package com.example.kts_android_09_2021.db.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = EditorialContract.TABLE_NAME
)
data class EditorialEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = EditorialContract.Columns.ID)
    val id: String,

    @ColumnInfo(name = EditorialContract.Columns.USERNAME)
    val userName: String,

    @ColumnInfo(name = EditorialContract.Columns.AVATAR_URL)
    val avatarUrl: String,

    @ColumnInfo(name = EditorialContract.Columns.IMAGE_URL)
    val imageUrl: String,

    @ColumnInfo(name = EditorialContract.Columns.LIKES)
    val likes: Int,

    @ColumnInfo(name = EditorialContract.Columns.LIKED_BY_USER)
    val likedByUser: Boolean
)