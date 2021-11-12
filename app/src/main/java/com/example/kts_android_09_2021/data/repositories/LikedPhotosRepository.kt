package com.example.kts_android_09_2021.data.repositories

import com.example.kts_android_09_2021.data.db.LikedPhotosDao
import com.example.kts_android_09_2021.data.enteties.LikedPhotosEntity
import com.example.kts_android_09_2021.domain.items.ItemUserPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LikedPhotosRepository(
    private val likedPhotosDao: LikedPhotosDao
) {

    suspend fun addLikedPhotosList(likedPhotosList: List<ItemUserPhoto?>) {
        likedPhotosDao.deleteAllLikedPhotos()
        likedPhotosDao.addListLikedPhotos(
            likedPhotosList.map {
                convertLikedPhotoInEntity(it)
            }
        )
    }

    fun getLikedPhotosList(): Flow<List<ItemUserPhoto>> {
        return likedPhotosDao.getListLikedPhotos().map {
            it.map { likedPhotos ->
                convertLikedPhotoEntityInItem(likedPhotos)
            }
        }
    }

    suspend fun deleteAllItems() {
        likedPhotosDao.deleteAllLikedPhotos()
    }

    private fun convertLikedPhotoInEntity(userPhotos: ItemUserPhoto?): LikedPhotosEntity {
        return LikedPhotosEntity(
            id = userPhotos!!.id,
            userName = userPhotos.userName,
            avatarUrl = userPhotos.avatarUrl!!,
            imageUrl = userPhotos.imageUrl
        )
    }

    private fun convertLikedPhotoEntityInItem(likedPhotosEntity: LikedPhotosEntity): ItemUserPhoto {
        return ItemUserPhoto(
            id = likedPhotosEntity.id,
            userName = likedPhotosEntity.userName,
            avatarUrl = likedPhotosEntity.avatarUrl,
            imageUrl = likedPhotosEntity.imageUrl
        )
    }
}