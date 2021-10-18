package com.example.kts_android_09_2021.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kts_android_09_2021.db.daos.EditorialDao
import com.example.kts_android_09_2021.db.enteties.EditorialEntity

@Database(
    entities = [
        EditorialEntity::class
    ], version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun editorialDao(): EditorialDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "database"
    }
}