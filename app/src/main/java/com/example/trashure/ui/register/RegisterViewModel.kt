package com.example.trashure.ui.register

import androidx.lifecycle.ViewModel
import com.example.trashure.data.repository.AuthRepository

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    fun register(
        name: String,
        email: String,
        password: String,
        nomor: String,
        provinsi: String,
        kabKota: String,
        kecamatan: String
    ) = repository.register(name, email, password, nomor, provinsi, kabKota, kecamatan)
}