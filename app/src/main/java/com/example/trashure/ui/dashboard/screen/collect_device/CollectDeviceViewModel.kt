package com.example.trashure.ui.dashboard.screen.collect_device

import androidx.lifecycle.ViewModel
import com.example.trashure.data.repository.Repository

class CollectDeviceViewModel(private val repository: Repository) : ViewModel() {

    fun predictItem(
        brand: String,
        model: String,
        storage: String,
        ram: String,
        screenSize: String,
        camera: String,
        batteryCapacity: String,
        tahunPemakaian: Int
    ) = repository.predictItem(
        brand,
        model,
        storage,
        ram,
        screenSize,
        camera,
        batteryCapacity,
        tahunPemakaian
    )
}