package com.example.kts_android_09_2021.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class NetworkingModule(
    interceptor: NetworkingInterceptor
) {

    val unsplashApi: UnsplashApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor {
                        Timber.e(it)
                    }
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor(interceptor)
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(UnsplashApi::class.java)

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"
    }
}