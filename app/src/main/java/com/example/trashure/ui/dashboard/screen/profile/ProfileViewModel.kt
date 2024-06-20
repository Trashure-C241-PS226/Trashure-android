package com.example.trashure.ui.dashboard.screen.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.model.User
import com.example.trashure.data.repository.Repository
import com.example.trashure.data.response.DetailUser
import com.example.trashure.data.response.GetUserByIdResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    private val _onLoad = MutableLiveData<Boolean>()
    val onLoad: LiveData<Boolean> = _onLoad

    private val _image = MutableLiveData<String>()
    val image: LiveData<String> = _image

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _subdistrict = MutableLiveData<String>()
    val subdistrict: LiveData<String> = _subdistrict

    private val _updateProfileResult = MutableLiveData<Result<GetUserByIdResponse>>()
    val updateProfileResult: LiveData<Result<GetUserByIdResponse>> = _updateProfileResult

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getUserById() {
        _onLoad.value = true
        viewModelScope.launch {
            val result = repository.getUserById()
            _onLoad.value = false
            result.fold(
                onSuccess = { response ->
                    val imageUrl = response.data?.image
                    if (imageUrl.isNullOrEmpty()) {

                    } else {
                        _image.value = imageUrl!!
                    }
                    _userName.value = response.data?.username ?: ""
                    _email.value = response.data?.email ?: ""
                    _phoneNumber.value = response.data?.nomor ?: ""
                    _province.value = response.data?.provinsi ?: ""
                    _city.value = response.data?.kabKota ?: ""
                    _subdistrict.value = response.data?.kecamatan ?: ""
                },
                onFailure = { error ->
                    Log.e("ProfileViewModel", "Error: ${error.message}")
                }
            )
        }
    }

    fun updateUser(user: DetailUser) {
        viewModelScope.launch {
            val result = repository.updateUser(user)
            _updateProfileResult.value = result
        }
    }
}