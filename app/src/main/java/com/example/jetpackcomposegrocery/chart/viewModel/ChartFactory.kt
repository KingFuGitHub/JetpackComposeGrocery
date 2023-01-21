package com.example.jetpackcomposegrocery.chart.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.ChartPreferences

class ChartFactory(
    private val chartPreferences: ChartPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override  fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(ChartViewModel::class.java)){
            return ChartViewModel(chartPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}