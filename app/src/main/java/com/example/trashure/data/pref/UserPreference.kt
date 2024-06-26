package com.example.trashure.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.trashure.data.model.Predict
import com.example.trashure.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: User) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
            )
        }
    }

    suspend fun savePredict(predict: Predict) {
        dataStore.edit { preferences ->
            preferences[CATEGORY] = predict.category
            preferences[HARGA] = predict.harga
        }
    }

    fun getPredict(): Flow<Predict> {
        return dataStore.data.map { preferences ->
            Predict(
                preferences[CATEGORY] ?: 0,
                preferences[HARGA] ?: ""
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        private val HARGA = stringPreferencesKey("harga")
        private val CATEGORY = intPreferencesKey("category")

        fun getInstance(context: Context): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val dataStore = context.dataStore
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}