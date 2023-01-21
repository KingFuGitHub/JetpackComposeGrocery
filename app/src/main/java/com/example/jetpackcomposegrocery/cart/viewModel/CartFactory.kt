package com.example.jetpackcomposegrocery.cart.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.CartPreferences
import com.example.jetpackcomposegrocery.repository.room.ItemDatabase

class CartFactory(
    private val cartPreferences: CartPreferences,
    private val cartDatabase: ItemDatabase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override  fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(cartPreferences, cartDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}