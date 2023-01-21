package com.example.jetpackcomposegrocery.repository.preferencesDataStore

import android.content.Context


class CartPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: CartPreferences? = null

        fun getInstance(context: Context): CartPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = CartPreferences(context = context)
                INSTANCE = instance
                return instance
            }
        }
    }


    /************************************************************** Clear All **************************************************************/




    /************************************************************** set **************************************************************/




    /************************************************************** get **************************************************************/



}