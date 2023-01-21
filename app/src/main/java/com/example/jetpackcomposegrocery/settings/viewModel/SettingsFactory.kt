package com.example.jetpackcomposegrocery.settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.SettingsPreferences

// responsible to create instance of ViewModel.
class SettingsFactory(
    private val settingsPreferences: SettingsPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(settingsPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}