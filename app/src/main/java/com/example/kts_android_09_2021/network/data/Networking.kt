package com.example.kts_android_09_2021.network.data

import android.util.Log
import com.example.kts_android_09_2021.key_value.DatastoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val NETWORKING_TAG = "NETWORKING_TAG"

    fun buildOkHttpClient(datastoreRepository: DatastoreRepository) {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Log.e(NETWORKING_TAG, it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )

        GlobalScope.launch(Dispatchers.IO) {
            datastoreRepository.observeTokenChanging().collect {
                if (!it.isNullOrEmpty()) {
                    okHttpClientBuilder.addNetworkInterceptor(
                        Interceptor { chain ->
                            val originalRequest = chain.request()

                            val newRequest =
                                originalRequest.newBuilder()
                                    .header("Authorization", "Bearer $it")
                                    .build()

                            chain.proceed(newRequest)
                        }
                    )
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(MoshiConverterFactory.create())
                        .client(okHttpClientBuilder.build())
                        .build()
                }
            }
        }
    }

    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val unsplashApi: UnsplashApi
        get() = retrofit.create()
}