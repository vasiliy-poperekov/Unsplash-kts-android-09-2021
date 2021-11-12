package com.example.kts_android_09_2021.data.api

import com.example.kts_android_09_2021.domain.enteties.photos_list.ListsPhoto
import com.example.kts_android_09_2021.domain.enteties.profile.AuthorizedUser
import com.example.kts_android_09_2021.domain.enteties.photos_list.ResponsedBody
import retrofit2.http.*

interface UnsplashApi {

    @GET("me")
    suspend fun getInfoAboutAuthorizedUser(): AuthorizedUser

    @GET("photos")
    suspend fun getPhotosList(
        @Query("page") pageNumber: Int
    ): List<ListsPhoto>

    @GET("users/{username}/photos")
    suspend fun getUsersPhotos(
        @Path("username") username: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsOnPage: Int
    ): List<ListsPhoto>

    @GET("users/{username}/likes")
    suspend fun getLikedPhotos(
        @Path("username") username: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsOnPage: Int
    ): List<ListsPhoto>

    @POST("photos/{id}/like")
    suspend fun likePhoto(
        @Path("id") id: String
    ): ResponsedBody

    @DELETE("photos/{id}/like")
    suspend fun unLikePhoto(
        @Path("id") id: String
    ): ResponsedBody
}