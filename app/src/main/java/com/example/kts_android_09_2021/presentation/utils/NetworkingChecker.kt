package com.example.kts_android_09_2021.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class NetworkingChecker(
    val context: Context
) {
    private var changesJob: Job? = null

    fun observeNetworkChange(): SharedFlow<Boolean> = callbackFlow {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()
        if (!isInternetAvailable(cm)) {
            send(false)
        }

        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                changesJob?.cancel()
                changesJob = CoroutineScope(Dispatchers.IO).launch {
                    send(true)
                }
            }

            override fun onLost(network: Network) {
                changesJob?.cancel()
                changesJob = CoroutineScope(Dispatchers.IO).launch {
                    send(false)
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(builder.build(), networkCallback)
        }

        awaitClose {
            cm.unregisterNetworkCallback(networkCallback)
        }
    }
        .shareIn(CoroutineScope(Dispatchers.IO), SharingStarted.Lazily, 1)


    private fun isInternetAvailable(cm: ConnectivityManager): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

        return result
    }
}