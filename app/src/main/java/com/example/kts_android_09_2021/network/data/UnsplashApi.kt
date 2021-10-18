package com.example.kts_android_09_2021.network.data

import com.example.kts_android_09_2021.network.entities.AuthorizedUser
import com.example.kts_android_09_2021.network.entities.photos_list.Photo
import com.example.kts_android_09_2021.network.entities.photos_list.ResponsedBody
import retrofit2.http.*

interface UnsplashApi {

    @GET("me")
    suspend fun getInfoAboutAuthorizedUser(): AuthorizedUser

    @GET("photos")
    suspend fun getPhotosList(
        @Query("page") pageNumber: Int
    ): List<Photo>

    @POST("photos/{id}/like")
    suspend fun likePhoto(
        @Path("id") id: String
    ): ResponsedBody

    @DELETE("photos/{id}/like")
    suspend fun unLikePhoto(
        @Path("id") id: String
    ): ResponsedBody
}