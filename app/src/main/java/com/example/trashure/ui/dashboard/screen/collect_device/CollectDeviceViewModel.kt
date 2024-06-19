package com.example.trashure.ui.dashboard.screen.collect_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trashure.data.model.Predict
import com.example.trashure.data.repository.Repository
import kotlinx.coroutines.launch

class CollectDeviceViewModel(private val repository: Repository) : ViewModel() {

    fun predictItem(
        brand: String,
        storage: String,
        ram: String,
        screenSize: String,
        camera: String,
        batteryCapacity: String,
        tahunPemakaian: Int
    ) = repository.predictItem(
        brand,
        storage,
        ram,
        screenSize,
        camera,
        batteryCapacity,
        tahunPemakaian
    )

    fun savePredict(predict: Predict) {
        viewModelScope.launch {
            repository.savePredict(predict)
        }
    }
}