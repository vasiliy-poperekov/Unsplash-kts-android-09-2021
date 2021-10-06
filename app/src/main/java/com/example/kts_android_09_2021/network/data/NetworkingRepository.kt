package com.example.kts_android_09_2021.network.data

import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.network.entities.AuthorizedUser

class NetworkingRepository {

    suspend fun getInfoAboutAuthorizedUser(): AuthorizedUser {
        return Networking.unsplashApi.getInfoAboutAuthorizedUser()
    }

    suspend fun getPhotosList(pageNumber: Int): List<ItemEditorial?> {
        return Networking.unsplashApi.getPhotosList(pageNumber).map {
            ItemEditorial(
                id = it.id,
                userName = it.user.userName,
                imageUrl = it.urls.full,
                avatarUrl = it.user.profileImage.medium!!,
                likes = it.likes,
                likedByUser = it.likedByUser
            )
        }
    }

    suspend fun likePhoto(itemEditorial: ItemEditorial): ItemEditorial {
        val returnedPhoto = Networking.unsplashApi.likePhoto(itemEditorial.id).photo
        return ItemEditorial(
            id = returnedPhoto.id,
            userName = returnedPhoto.user.userName,
            imageUrl = returnedPhoto.urls.full,
            avatarUrl = returnedPhoto.user.profileImage.medium!!,
            likes = returnedPhoto.likes,
            likedByUser = returnedPhoto.likedByUser
        )
    }

}