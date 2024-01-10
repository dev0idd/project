package com.example.mega

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

data class UserInfo(
    var isLogin: Boolean,
    var login: String,
    var userId: String,
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")

class DataStoreManager(val context: Context) {
    suspend fun saveAuthInfo(userInfo: UserInfo) {
        context.dataStore.edit { pref ->
            pref[booleanPreferencesKey("is_login")] = userInfo.isLogin
            pref[stringPreferencesKey("login")] = userInfo.login
            pref[stringPreferencesKey("userId")] = userInfo.userId
        }
    }

    fun getAuthInfo() = context.dataStore.data.map { pref ->
        return@map UserInfo(
            pref[booleanPreferencesKey("is_login")] ?: false,
            pref[stringPreferencesKey("login")] ?: "",
            pref[stringPreferencesKey("userId")] ?: ""
        )
    }
}