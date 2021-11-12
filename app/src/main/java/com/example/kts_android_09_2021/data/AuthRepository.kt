package com.example.kts_android_09_2021.data

import android.net.Uri
import com.example.kts_android_09_2021.domain.common.AuthConfig.ACCESS_KEY
import com.example.kts_android_09_2021.domain.common.AuthConfig.AUTH_URI
import com.example.kts_android_09_2021.domain.common.AuthConfig.REDIRECT_URI
import com.example.kts_android_09_2021.domain.common.AuthConfig.RESPONSE_TYPE
import com.example.kts_android_09_2021.domain.common.AuthConfig.SCOPE
import com.example.kts_android_09_2021.domain.common.AuthConfig.SECRET_KET
import com.example.kts_android_09_2021.domain.common.AuthConfig.TOKEN_URI
import net.openid.appauth.*

class AuthRepository {

    private fun getServiceConfig(): AuthorizationServiceConfiguration =
        AuthorizationServiceConfiguration(
            Uri.parse(AUTH_URI),
            Uri.parse(TOKEN_URI)
        )

    fun getAuthRequest(): AuthorizationRequest =
        AuthorizationRequest.Builder(
            getServiceConfig(),
            ACCESS_KEY,
            RESPONSE_TYPE,
            Uri.parse(REDIRECT_URI)
        )
            .setCodeVerifier(null)
            .setScope(SCOPE)
            .build()

    fun getAccessToken(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: (String) -> Unit,
        onError: (AuthorizationException) -> Unit
    ) {
        authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, ex ->
            if (response != null) {
                val accessToken = response.accessToken.orEmpty()
                onComplete(accessToken)
            } else {
                onError(ex!!)
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(SECRET_KET)
    }
}