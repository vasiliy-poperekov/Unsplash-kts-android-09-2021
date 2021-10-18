package com.example.kts_android_09_2021

import android.app.Application
import com.example.kts_android_09_2021.db.Database
import com.example.kts_android_09_2021.key_value.DatastoreRepository
import com.example.kts_android_09_2021.network.data.Networking
import com.example.kts_android_09_2021.utils.NetworkingChecker
import timber.log.Timber

class MainApplication : Application() {
    lateinit var datastoreRepository: DatastoreRepository

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        NetworkingChecker(this).startNetworkCallback()
        datastoreRepository = DatastoreRepository(this)
        Database.init(this)
        Networking.buildOkHttpClient(datastoreRepository)
    }

    override fun onTerminate() {
        NetworkingChecker(this).stopNetworkCallback()
        super.onTerminate()
    }
}