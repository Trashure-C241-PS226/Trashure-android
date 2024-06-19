package com.example.trashure.ui.setor.prediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.trashure.data.model.Predict
import com.example.trashure.data.repository.Repository

class PredictionViewModel(private val repository: Repository) : ViewModel() {

    fun getPredict(): LiveData<Predict> {
        return repository.getPredict().asLiveData()
    }
}