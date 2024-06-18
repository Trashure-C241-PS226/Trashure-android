package com.example.trashure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.trashure.data.api.config.AuthApiConfig
import com.example.trashure.data.api.service.ApiService
import com.example.trashure.data.model.User
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.data.response.GetUserByIdResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    fun getDetailUser(userId: Int): LiveData<ApiResponse<GetUserByIdResponse>> = liveData {
        val userSession = userPreference.getSession().firstOrNull()
        if (userSession != null && !userSession.token.isNullOrEmpty()) {
            val token = userSession.token
            val apiService = AuthApiConfig.getApiService(token)
            emit(ApiResponse.Loading)
            try {
                val response = apiService.getUserById(userId)
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, GetUserByIdResponse::class.java)
                val errorMessage = errorBody.message
                emit(ApiResponse.Error(errorMessage))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        } else {
            emit(ApiResponse.Error("Error"))
        }
    }
}