package com.example.kts_android_09_2021.data.repositories

import com.example.kts_android_09_2021.data.db.UserPhotosDao
import com.example.kts_android_09_2021.data.enteties.UserPhotosEntity
import com.example.kts_android_09_2021.domain.items.ItemUserPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPhotosRepository(
    private val userPhotosDao: UserPhotosDao
) {

    suspend fun addUserPhotosList(userPhotosList: List<ItemUserPhoto?>) {
        userPhotosDao.deleteAllUserPhotos()
        userPhotosDao.addListUserPhotos(
            userPhotosList.map {
                convertUserPhotoInEntity(it)
            }
        )
    }

    fun getUserPhotosList(): Flow<List<ItemUserPhoto>> {
        return userPhotosDao.getListUserPhotos().map {
            it.map { userPhotos ->
                convertUserPhotoEntityInItem(userPhotos)
            }
        }
    }

    suspend fun deleteAllItems() {
        userPhotosDao.deleteAllUserPhotos()
    }

    private fun convertUserPhotoInEntity(userPhoto: ItemUserPhoto?): UserPhotosEntity {
        return UserPhotosEntity(
            id = userPhoto!!.id,
            userName = userPhoto.userName,
            avatarUrl = userPhoto.avatarUrl!!,
            imageUrl = userPhoto.imageUrl
        )
    }

    private fun convertUserPhotoEntityInItem(userPhotoEntity: UserPhotosEntity): ItemUserPhoto {
        return ItemUserPhoto(
            id = userPhotoEntity.id,
            userName = userPhotoEntity.userName,
            avatarUrl = userPhotoEntity.avatarUrl,
            imageUrl = userPhotoEntity.imageUrl
        )
    }
}