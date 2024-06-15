package com.example.trashure.data.model

data class User(

    val email: String,
    val token: String,
    val isLogin: Boolean = false
)