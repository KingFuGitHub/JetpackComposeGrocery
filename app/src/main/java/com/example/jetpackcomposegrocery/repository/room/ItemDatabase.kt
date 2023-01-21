package com.example.jetpackcomposegrocery.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemData::class], version = 1)
abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDAO

    companion object{
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase{
            return INSTANCE ?: synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "item_list_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
