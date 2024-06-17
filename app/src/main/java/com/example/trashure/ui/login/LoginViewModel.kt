package com.example.trashure.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.model.User
import com.example.trashure.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun saveSession(user: User) {
        viewModelScope.launch {
            authRepository.saveSession(user)
        }
    }

    fun login(name: String, email: String) = authRepository.login(name, email)
}