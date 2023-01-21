package com.example.jetpackcomposegrocery.repository.room

import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDAO: ItemDAO) {

    val readAllData: Flow<List<ItemData>> = itemDAO.getAll()

    suspend fun addItem(itemData: ItemData){
        itemDAO.insert(itemData)
    }

    suspend fun updateItem(itemData: ItemData){
        itemDAO.update(itemData)
    }

    suspend fun deleteItem(itemData: ItemData){
        itemDAO.delete(itemData)
    }

    suspend fun deleteAllItems(){
        itemDAO.deleteAllItems()
    }

}