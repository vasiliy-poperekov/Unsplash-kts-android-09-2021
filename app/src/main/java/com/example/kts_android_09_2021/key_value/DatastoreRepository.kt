package com.example.kts_android_09_2021.key_value

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastoreRepository(
    context: Context
) {

    private val Context.dataStore by preferencesDataStore(
        name = DATASTORE_NAME
    )

    private val dataStore = context.dataStore

    suspend fun saveToken(accessToken: String) {
        dataStore.edit {
            it[KEY_TOKEN] = accessToken
        }
    }

    suspend fun saveEnterFlag(isEntered: Boolean) {
        dataStore.edit {
            it[KEY_IS_NOT_FIRST_ENTER] = isEntered
        }
    }

    fun observeTokenChanging(): Flow<String?> {
        return dataStore.data.map {
            it[KEY_TOKEN]
        }
    }

    fun observeEnterChanging(): Flow<Boolean?> {
        return dataStore.data.map {
            it[KEY_IS_NOT_FIRST_ENTER]
        }
    }

    companion object {
        private const val DATASTORE_NAME = "datastore"
        private val KEY_TOKEN = stringPreferencesKey("KEY_STRING")
        private val KEY_IS_NOT_FIRST_ENTER = booleanPreferencesKey("KEY_BOOLEAN")
    }
}