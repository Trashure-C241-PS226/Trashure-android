package com.example.trashure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.trashure.data.api.service.ApiService
import com.example.trashure.data.model.User
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.data.response.CreateItemResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    fun predictItem(
        brand: String,
        model: String,
        storage: String,
        ram: String,
        screenSize: String,
        camera: String,
        batteryCapacity: String,
        tahunPemakaian: Int
    ): LiveData<ApiResponse<CreateItemResponse>> = liveData {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.postItem(
                brand,
                model,
                storage,
                ram,
                screenSize,
                camera,
                batteryCapacity,
                tahunPemakaian
            )
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, CreateItemResponse::class.java)
            val errorMessage = errorBody.message
            emit(ApiResponse.Error(errorMessage))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }
}