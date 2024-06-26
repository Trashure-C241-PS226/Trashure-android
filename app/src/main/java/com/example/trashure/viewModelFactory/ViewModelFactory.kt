package com.example.trashure.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trashure.data.repository.Repository
import com.example.trashure.di.Injection
import com.example.trashure.ui.dashboard.screen.collect_device.CollectDeviceViewModel
import com.example.trashure.ui.dashboard.screen.home.HomeViewModel
import com.example.trashure.ui.dashboard.screen.profile.ProfileViewModel
import com.example.trashure.ui.setor.prediction.PredictionViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CollectDeviceViewModel::class.java) -> {
                CollectDeviceViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PredictionViewModel::class.java) -> {
                PredictionViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return instance as ViewModelFactory
        }
    }
}