package com.example.jetpackcomposegrocery.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// make constructor private, use get instance to get the class instead.
class SettingsPreferences private constructor(context: Context) {


    // Singleton object, can be accessed directly. Initialized when the class is loaded.
    companion object{

        // Volatile for the algorithm to be more efficient
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        // get instance of the class using double-checked locking
        fun getInstance(context: Context): SettingsPreferences {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                if (INSTANCE != null) {
                    return INSTANCE!!
                }
                val instance = SettingsPreferences(context = context)
                INSTANCE = instance
                return INSTANCE!!
            }
        }

    }

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "SettingsDataStore")
    private val settingsDataStore: DataStore<Preferences> = context.settingsDataStore

    private val settingsIsDarkThemeDefault = false

    private val _settingsIsDarkTheme = booleanPreferencesKey("settingsThemeIsDark")

    /************************************************************** Clear All **************************************************************/

    suspend fun clearAll() {
        settingsDataStore.edit {
            it.clear()
        }
    }


    /************************************************************** set **************************************************************/

    suspend fun setSettingsIsDarkTheme(boolean: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[_settingsIsDarkTheme] = boolean
        }
    }

    /************************************************************** get **************************************************************/


    val getSettingsIsDarkTheme: Flow<Boolean> = settingsDataStore.data
        .map { preferences ->
            preferences[_settingsIsDarkTheme] ?: settingsIsDarkThemeDefault
        }


}