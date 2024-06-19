package com.example.trashure.data.api.service

import com.example.trashure.data.response.GetUserByIdResponse
import com.example.trashure.data.response.PredictResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUserById(
        @Path("user_Id") userId: Int
    ): GetUserByIdResponse

    @FormUrlEncoded
    @POST("users/items/predict")
    suspend fun postItem(
        @Field("brand") brand: String,
        @Field("storage") storage: String,
        @Field("ram") ram: String,
        @Field("screen_size") screenSize: String,
        @Field("camera") camera: String,
        @Field("battery_capacity") batteryCapacity: String,
        @Field("tahun_pemakaian") tahunPemakaian: Int
    ): PredictResponse
}