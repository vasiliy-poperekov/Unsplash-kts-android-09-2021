package com.example.kts_android_09_2021.network.auth

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE =
        "public read_user write_user read_photos write_photos write_likes write_followers read_collections write_collections"

    //    const val ACCESS_KEY = "-7nybGaaVVinUEfZ1PEtWVHRq_v2rd7KkzsTHZussJw"
//    const val SECRET_KET = "TcyDvd9mD8p2r96ib_WgNKraqg6KN4iE1rIx48y8VOI"
    const val ACCESS_KEY = "ljgsDf9PPlSw9pAOldnJVbpQQeyB8B4jOLSff75R3SI"
    const val SECRET_KET = "9AGELkkQvRSIX4sui6T-T-g-6_4dnnR7gVYVrG04nT4"
    const val REDIRECT_URI = "my.special.scheme://data"

}