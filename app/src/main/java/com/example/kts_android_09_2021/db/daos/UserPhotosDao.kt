package com.example.kts_android_09_2021.db.daos

import androidx.room.*
import com.example.kts_android_09_2021.db.enteties.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListUserPhotos(likedPhotosList: List<UserPhotosEntity>)

    @Query("DELETE FROM ${UserPhotosContract.TABLE_NAME}")
    suspend fun deleteAllUserPhotos()

    @Query("SELECT * FROM ${UserPhotosContract.TABLE_NAME}")
    fun getListUserPhotos(): Flow<List<UserPhotosEntity>>
}