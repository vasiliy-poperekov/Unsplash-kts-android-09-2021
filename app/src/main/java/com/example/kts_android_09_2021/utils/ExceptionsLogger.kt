package com.example.kts_android_09_2021.utils

import retrofit2.HttpException
import timber.log.Timber

fun logExceptions(exception: Throwable) {
    if (exception is HttpException)
        when (exception.code()) {
            400 -> Timber.e("The request was unacceptable, often due to missing a required parameter")
            401 -> Timber.e("Invalid Access Token")
            403 -> Timber.e("Missing permissions to perform request")
            404 -> Timber.e("The requested resource doesn’t exist")
            500, 503 -> Timber.e("Something went wrong on server end")
            else -> Timber.e(exception)
        }
    else Timber.e(exception)
}