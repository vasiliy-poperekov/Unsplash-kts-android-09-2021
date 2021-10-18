package com.example.kts_android_09_2021.network.data

import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.network.entities.AuthorizedUser
import com.example.kts_android_09_2021.utils.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkingRepository {

    fun getInfoAboutAuthorizedUser(): Flow<AuthorizedUser> = flow {
        emit(Networking.unsplashApi.getInfoAboutAuthorizedUser())
    }

    fun getPhotosList(pageNumber: Int): Flow<List<ItemEditorial?>> {
        return flow {
            emit(
                Networking.unsplashApi.getPhotosList(pageNumber).map {
                    Converter.convertPhotoInItem(it)
                }
            )
        }
    }

    fun likePhoto(itemEditorial: ItemEditorial): Flow<ItemEditorial> = flow {
        emit(Converter.convertPhotoInItem(Networking.unsplashApi.likePhoto(itemEditorial.id).photo))
    }


    fun unLikePhoto(itemEditorial: ItemEditorial): Flow<ItemEditorial> = flow {
        emit(Converter.convertPhotoInItem(Networking.unsplashApi.unLikePhoto(itemEditorial.id).photo))
    }

}