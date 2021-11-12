package com.example.kts_android_09_2021.data.api

import com.example.kts_android_09_2021.data.DatastoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

class NetworkingInterceptor(
    private val datastoreRepository: DatastoreRepository
) : Interceptor {
    private var token = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            datastoreRepository.observeTokenChanging().collect { _token ->
                if (!_token.isNullOrEmpty()) {
                    token = _token
                }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        var response = chain.proceed(newRequest)

        while (response.code == 401) {
            newRequest = newRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            response = chain.proceed(newRequest)
        }

        return response
    }
}