package com.example.trashure.di

import android.content.Context
import com.example.trashure.data.api.config.AuthApiConfig
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AuthInjection {
    fun provideRepositoryAuth(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context)
        val user = runBlocking { pref.getSession().first() }
        val apiService = AuthApiConfig.getApiService(user.token)
        return AuthRepository(pref, apiService)
    }
}