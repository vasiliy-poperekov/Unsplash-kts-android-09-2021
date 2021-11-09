package com.example.kts_android_09_2021.db.repositories_impl

import com.example.kts_android_09_2021.db.daos.UserPhotosDao
import com.example.kts_android_09_2021.db.enteties.UserPhotosEntity
import com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment.ItemUserPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPhotosRepositoryImpl(
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