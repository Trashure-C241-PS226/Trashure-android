package com.example.trashure.ui.dashboard.screen.collect_device

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectDeviceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is collect device Fragment"
    }
    val text: LiveData<String> = _text
}