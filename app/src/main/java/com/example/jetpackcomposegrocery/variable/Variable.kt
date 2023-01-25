package com.example.jetpackcomposegrocery.variable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Variable {
    var isDarkTheme by mutableStateOf(false)
    var isShowSnackbar by mutableStateOf(false)
    var showMenu by mutableStateOf(false)
    var resetInputText by mutableStateOf(false)
}