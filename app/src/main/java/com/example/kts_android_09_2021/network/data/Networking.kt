package com.example.kts_android_09_2021.network.data

import android.util.Log
import com.example.kts_android_09_2021.network.entities.AuthData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val NETWORKING_TAG = "NETWORKING_TAG"

    private val okHttpClient = buildOkHttpClient()

    private fun buildOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Log.e(NETWORKING_TAG, it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )

        if (AuthData.accessToken != null) {
            okHttpClientBuilder.addNetworkInterceptor(
                Interceptor { chain ->
                    val originalRequest = chain.request()

                    val newRequest =
                        originalRequest.newBuilder()
                            .header("Authorization", "Bearer ${AuthData.accessToken!!}")
                            .build()

                    chain.proceed(newRequest)
                }
            )
        }

        return okHttpClientBuilder.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val unsplashApi: UnsplashApi
        get() = retrofit.create()
}