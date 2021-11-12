package com.example.kts_android_09_2021.data.db

import androidx.room.*
import com.example.kts_android_09_2021.data.enteties.UserPhotosEntity
import com.example.kts_android_09_2021.domain.contracts.UserPhotosContract
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