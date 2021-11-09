package com.example.kts_android_09_2021.network.entities

import com.example.kts_android_09_2021.network.entities.photos_list.Urls

abstract class Photo(
    open val id: String,
    open val urls: Urls
)
