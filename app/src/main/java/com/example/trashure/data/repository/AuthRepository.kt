package com.example.trashure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.trashure.data.api.service.AuthApiService
import com.example.trashure.data.model.User
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.data.response.LoginResponse
import com.example.trashure.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException

class AuthRepository(
    private val userPreference: UserPreference,
    private val authApiService: AuthApiService
) {

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun register(
        name: String,
        email: String,
        password: String,
        nomor: String,
        provinsi: String,
        kabKota: String,
        kecamatan: String
    ): LiveData<ApiResponse<RegisterResponse>> = liveData {
        emit(ApiResponse.Loading)
        try {
            val response =
                authApiService.register(name, email, password, nomor, provinsi, kabKota, kecamatan)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(ApiResponse.Error(errorMessage))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<ApiResponse<LoginResponse>> = liveData {
        emit(ApiResponse.Loading)
        try {
            val response = authApiService.login(email, password)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ApiResponse.Error(errorMessage))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }
}