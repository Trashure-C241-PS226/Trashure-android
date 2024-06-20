package com.example.trashure.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.trashure.data.api.service.ApiService
import com.example.trashure.data.model.Predict
import com.example.trashure.data.model.User
import com.example.trashure.data.pref.UserPreference
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.data.response.DetailUser
import com.example.trashure.data.response.GetUserByIdResponse
import com.example.trashure.data.response.PredictResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun savePredict(predict: Predict) {
        userPreference.savePredict(predict)
    }

    fun getPredict(): Flow<Predict> {
        return userPreference.getPredict()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun predictItem(
        brand: String,
        storage: String,
        ram: String,
        screenSize: String,
        camera: String,
        batteryCapacity: String,
        tahunPemakaian: Int
    ): LiveData<ApiResponse<PredictResponse>> = liveData {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.postItem(
                brand,
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
            val errorBody = Gson().fromJson(jsonInString, PredictResponse::class.java)
            val errorMessage = errorBody.message
            emit(ApiResponse.Error(errorMessage))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }

    suspend fun getUserById(): Result<GetUserByIdResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUserById()
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun updateUser(user: DetailUser): Result<GetUserByIdResponse> {
        return try {
            val response = apiService.updateUser(user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}