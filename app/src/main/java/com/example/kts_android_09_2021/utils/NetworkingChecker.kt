package com.example.kts_android_09_2021.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NetworkingChecker(
    val context: Context
) {
    private var currentJob: Job? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            currentJob?.cancel()
            currentJob = GlobalScope.launch(Dispatchers.IO) {
                Variables.isNetworkingConnectionState.emit(true)
            }
        }

        override fun onLost(network: Network) {
            currentJob?.cancel()
            currentJob = GlobalScope.launch(Dispatchers.IO) {
                Variables.isNetworkingConnectionState.emit(false)
            }
        }
    }

    fun startNetworkCallback() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    fun stopNetworkCallback() {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }
}