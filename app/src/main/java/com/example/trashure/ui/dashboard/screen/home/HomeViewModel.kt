package com.example.trashure.ui.dashboard.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trashure.data.model.User
import com.example.trashure.data.repository.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }
}