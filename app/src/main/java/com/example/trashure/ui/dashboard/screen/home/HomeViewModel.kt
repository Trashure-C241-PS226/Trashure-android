package com.example.trashure.ui.dashboard.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.model.User
import com.example.trashure.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

    fun getUserById() {
        viewModelScope.launch {
            val result = repository.getUserById()
            result.fold(
                onSuccess = { response ->
                    _userName.value = response.data?.username ?: ""
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "Error: ${error.message}")
                }
            )
        }
    }
}