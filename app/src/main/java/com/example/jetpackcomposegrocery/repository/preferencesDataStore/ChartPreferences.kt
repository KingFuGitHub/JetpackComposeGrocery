package com.example.jetpackcomposegrocery.repository.preferencesDataStore

import android.content.Context

class ChartPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: ChartPreferences? = null

        fun getInstance(context: Context): ChartPreferences{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?.let{
                    return it
                }
                val instance = ChartPreferences(context)
                INSTANCE = instance
                return instance

            }
        }
    }


    /************************************************************** Clear All **************************************************************/


    /************************************************************** set **************************************************************/


    /************************************************************** get **************************************************************/

}