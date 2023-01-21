package com.example.jetpackcomposegrocery.chart.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposegrocery.item.Item
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.ChartPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChartViewModel(
    private val chartPreferences: ChartPreferences
) : ViewModel() {

    var currentQuantityPercentage by mutableStateOf(0.0f)
        private set

    var sumQuantity by mutableStateOf(0.0)
        private set

    var totalQuantity by mutableStateOf(0.0)
        private set

    fun sumOfQuantity() {
        sumQuantity = 0.0
        viewModelScope.launch {
            while (sumQuantity < totalQuantity) {
                delay(25)
                sumQuantity += totalQuantity * 0.025
                currentQuantityPercentage = (sumQuantity / totalQuantity).toFloat()
            }
        }
    }

    fun totalOfQuantity() {
        totalQuantity = 0.0
        viewModelScope.launch {
            for (i in 0 until Item.itemList.size) {
                totalQuantity += Item.itemList[i].quantity.toDouble()
            }
        }
    }


}