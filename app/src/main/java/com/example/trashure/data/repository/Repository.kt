package com.example.trashure.data.repository

import com.example.trashure.data.api.service.ApiService
import com.example.trashure.data.model.User
import com.example.trashure.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }
}