package com.example.kts_android_09_2021.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kts_android_09_2021.db.daos.EditorialDao
import com.example.kts_android_09_2021.db.daos.LikedPhotosDao
import com.example.kts_android_09_2021.db.daos.UserDao
import com.example.kts_android_09_2021.db.daos.UserPhotosDao
import com.example.kts_android_09_2021.db.enteties.EditorialEntity
import com.example.kts_android_09_2021.db.enteties.LikedPhotosEntity
import com.example.kts_android_09_2021.db.enteties.UserEntity
import com.example.kts_android_09_2021.db.enteties.UserPhotosEntity

@Database(
    entities = [
        EditorialEntity::class,
        UserEntity::class,
        UserPhotosEntity::class,
        LikedPhotosEntity::class
    ], version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun editorialDao(): EditorialDao
    abstract fun userDao(): UserDao
    abstract fun userPhotosDao(): UserPhotosDao
    abstract fun likedPhotosDao(): LikedPhotosDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "database"
    }
}