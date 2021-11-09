package com.example.kts_android_09_2021.network.data

import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment.ItemUserPhoto
import com.example.kts_android_09_2021.network.entities.photos_list.ListsPhoto
import com.example.kts_android_09_2021.network.entities.profile.AuthorizedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class NetworkingRepository(
    private val unsplashApi: UnsplashApi
) {

    fun getInfoAboutAuthorizedUser(): Flow<AuthorizedUser> = flow {
        emit(unsplashApi.getInfoAboutAuthorizedUser())
    }

    fun getPhotosList(pageNumber: Int): Flow<List<ItemEditorial?>> {
        return flow {
            emit(
                unsplashApi.getPhotosList(pageNumber).map {
                    convertPhotoInItemEditorial(it)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    fun getUsersPhotos(
        username: String,
        pageNumber: Int,
        itemsOnPage: Int
    ): Flow<List<ItemUserPhoto>> {
        return flow {
            emit(unsplashApi.getUsersPhotos(username, pageNumber, itemsOnPage).map {
                convertPhotoInItemUserPhoto(it)
            })
        }.flowOn(Dispatchers.IO)
    }

    fun getLikedPhotos(
        username: String,
        pageNumber: Int,
        itemsOnPage: Int
    ): Flow<List<ItemUserPhoto>> {
        return flow {
            emit(unsplashApi.getLikedPhotos(username, pageNumber, itemsOnPage).map {
                convertPhotoInItemUserPhoto(it)
            })
        }.flowOn(Dispatchers.IO)
    }

    fun likePhoto(itemEditorial: ItemEditorial): Flow<ItemEditorial> = flow {
        emit(convertPhotoInItemEditorial(unsplashApi.likePhoto(itemEditorial.id).photo))
    }.flowOn(Dispatchers.IO)


    fun unLikePhoto(itemEditorial: ItemEditorial): Flow<ItemEditorial> = flow {
        emit(convertPhotoInItemEditorial(unsplashApi.unLikePhoto(itemEditorial.id).photo))
    }.flowOn(Dispatchers.IO)

    private fun convertPhotoInItemEditorial(photo: ListsPhoto): ItemEditorial {
        return ItemEditorial(
            id = photo.id,
            userName = photo.user.userName,
            imageUrl = photo.urls.full,
            avatarUrl = photo.user.avatarPhoto.medium,
            likes = photo.likes,
            likedByUser = photo.likedByUser
        )
    }

    private fun convertPhotoInItemUserPhoto(photo: ListsPhoto): ItemUserPhoto {
        return ItemUserPhoto(
            id = photo.id,
            userName = photo.user.userName,
            imageUrl = photo.urls.full,
            avatarUrl = photo.user.avatarPhoto.medium
        )
    }
}