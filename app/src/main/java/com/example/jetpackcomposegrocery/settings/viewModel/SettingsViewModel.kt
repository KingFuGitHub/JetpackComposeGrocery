package com.example.jetpackcomposegrocery.settings.viewModel

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.SettingsPreferences
import com.example.jetpackcomposegrocery.variable.Variable.isDarkTheme
import kotlinx.coroutines.flow.first

class SettingsViewModel(
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {

    suspend fun loadSettingsIsDarkTheme(){
        isDarkTheme = settingsPreferences.getSettingsIsDarkTheme.first()
    }

    suspend fun saveSettingsIsDarkTheme(){
        settingsPreferences.setSettingsIsDarkTheme(isDarkTheme)
    }

}