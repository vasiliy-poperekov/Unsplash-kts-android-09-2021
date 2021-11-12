package com.example.kts_android_09_2021.data.db

import androidx.room.*
import com.example.kts_android_09_2021.data.enteties.LikedPhotosEntity
import com.example.kts_android_09_2021.domain.contracts.LikedPhotosContract
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedPhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListLikedPhotos(likedPhotosList: List<LikedPhotosEntity>)

    @Query("DELETE FROM ${LikedPhotosContract.TABLE_NAME}")
    suspend fun deleteAllLikedPhotos()

    @Query("SELECT * FROM ${LikedPhotosContract.TABLE_NAME}")
    fun getListLikedPhotos(): Flow<List<LikedPhotosEntity>>
}