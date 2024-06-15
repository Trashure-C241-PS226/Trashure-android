package com.example.trashure.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.model.User
import com.example.trashure.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(name: String, email: String) = repository.login(name, email)
}