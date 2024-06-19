package com.example.trashure.ui.dashboard.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trashure.data.model.User
import com.example.trashure.data.repository.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

    fun getUserById(userId: Int) = repository.getUserById(userId)

}