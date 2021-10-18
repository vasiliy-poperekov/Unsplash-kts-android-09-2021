package com.example.kts_android_09_2021.db

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var appDatabase: AppDatabase
        private set

    fun init(context: Context) {
        appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            .build()
    }
}