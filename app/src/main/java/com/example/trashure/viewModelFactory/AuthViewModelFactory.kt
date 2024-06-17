package com.example.trashure.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trashure.data.repository.AuthRepository
import com.example.trashure.di.AuthInjection
import com.example.trashure.ui.login.LoginViewModel
import com.example.trashure.ui.register.RegisterViewModel

class AuthViewModelFactory(private val repository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: AuthViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): AuthViewModelFactory {
            if (instance == null) {
                synchronized(AuthViewModelFactory::class.java) {
                    instance = AuthViewModelFactory(AuthInjection.provideRepositoryAuth(context))
                }
            }
            return instance as AuthViewModelFactory
        }
    }
}