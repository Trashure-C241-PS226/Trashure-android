package com.example.trashure.data.api.service

import com.example.trashure.data.response.LoginResponse
import com.example.trashure.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("nomor") nomor: String,
        @Field("provinsi") provinsi: String,
        @Field("kab_kota") kabKota: String,
        @Field("kecamatan") kecamatan: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse
}