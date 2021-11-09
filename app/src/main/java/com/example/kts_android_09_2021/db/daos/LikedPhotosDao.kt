package com.example.kts_android_09_2021.db.daos

import androidx.room.*
import com.example.kts_android_09_2021.db.enteties.*
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