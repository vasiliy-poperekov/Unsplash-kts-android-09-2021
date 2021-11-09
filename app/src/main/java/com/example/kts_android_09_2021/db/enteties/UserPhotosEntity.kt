package com.example.kts_android_09_2021.db.enteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = UserPhotosContract.TABLE_NAME
)
data class UserPhotosEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = UserPhotosContract.Columns.ID)
    val id: String,

    @ColumnInfo(name = UserPhotosContract.Columns.USERNAME)
    val userName: String,

    @ColumnInfo(name = UserPhotosContract.Columns.AVATAR_URL)
    val avatarUrl: String,

    @ColumnInfo(name = UserPhotosContract.Columns.IMAGE_URL)
    val imageUrl: String
)