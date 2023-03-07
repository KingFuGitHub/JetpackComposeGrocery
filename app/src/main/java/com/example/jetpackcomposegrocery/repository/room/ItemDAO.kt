package com.example.jetpackcomposegrocery.repository.room

import androidx.room.*

// Database Access Object
@Dao
interface ItemDAO {
    @Query("SELECT * FROM item_list ORDER BY date DESC")
    suspend fun getAll(): List<ItemData>

//    @Query("SELECT * from item_list where itemId = :id")
//    fun getById(id:Int): ItemData?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ItemData)

    @Delete
    suspend fun delete(item: ItemData)

    @Update
    suspend fun update(item: ItemData)

    @Query("DELETE FROM item_list")
    suspend fun deleteAllItems()
}