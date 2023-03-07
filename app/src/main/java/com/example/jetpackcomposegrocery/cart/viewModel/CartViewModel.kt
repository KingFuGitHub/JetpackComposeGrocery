package com.example.jetpackcomposegrocery.cart.viewModel

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.toMutableStateList
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.components.showSnackbar
import com.example.jetpackcomposegrocery.item.Item
import com.example.jetpackcomposegrocery.item.Item.currentSelectedItemIndex
import com.example.jetpackcomposegrocery.item.Item.deletedItem
import com.example.jetpackcomposegrocery.item.Item.isItemDeleteSuccessful
import com.example.jetpackcomposegrocery.item.Item.itemList
import com.example.jetpackcomposegrocery.item.Item.items
import com.example.jetpackcomposegrocery.item.Item.removeItem
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.CartPreferences
import com.example.jetpackcomposegrocery.repository.room.ItemData
import com.example.jetpackcomposegrocery.repository.room.ItemDatabase
import com.example.jetpackcomposegrocery.variable.Variable.resetInputText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class CartViewModel(
    private val cartPreferences: CartPreferences,
    private val cartDatabase: ItemDatabase
) : ViewModel() {

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadItems()
            }
        }
    }

    fun convertLongToDate(timeInMillis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(ZoneId.systemDefault())
        return Instant.ofEpochMilli(timeInMillis).atZone(ZoneId.systemDefault()).toLocalDate()
            .format(formatter)
    }

    fun formatNumberWithCommas(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number)
    }

    private fun isDuplicateID(ID: String): Boolean {
        var result = true
        for (i in 0 until itemList.size) {
            if (itemList[i].ID == ID) {
                result = false
                break
            }
        }
        return result
    }

    fun setCurrentSelectedItem(index: Int) {
        currentSelectedItemIndex = index
    }

    fun addToCart(
        itemName: String,
        itemID: String,
        itemQuantity: String,
        scaffoldState: ScaffoldState,
        navController: NavHostController
    ) {

        viewModelScope.launch {
            try {
                if (itemName.isNotBlank() && itemID.isNotBlank() && itemQuantity.isNotBlank() &&
                    itemQuantity.toInt() > 0 && itemQuantity.toInt() < Int.MAX_VALUE && itemQuantity.isDigitsOnly()
                ) {
                    if (!isDuplicateID(itemID)) {
                        showSnackBarMessage(
                            message = "ID already exist",
                            scaffoldState = scaffoldState
                        )
                        return@launch
                    }

                    val item = ItemData(
                        itemName,
                        itemID,
                        itemQuantity.toInt(),
                        System.currentTimeMillis()
                    )
                    Item.addItem(item)
                    saveItem(item)
                    resetInputText = true
                    showSuccessMessage(
                        "Add to cart",
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                } else {
                    showSnackBarMessage("Invalid input(s)", scaffoldState = scaffoldState)
                }
            } catch (e: java.lang.Exception) {
                showSnackBarMessage("Invalid input(s)", scaffoldState = scaffoldState)
            }
        }
    }

    fun showSnackBarMessage(message: String, scaffoldState: ScaffoldState) {
        viewModelScope.launch {
            showSnackbar(
                message = message,
                actionLabel = "Cancel",
                duration = SnackbarDuration.Short,
                scaffoldState = scaffoldState,
                action = {},
                dismiss = {}
            )
        }
    }


    private fun showSuccessMessage(
        name: String,
        scaffoldState: ScaffoldState,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            showSnackbar(
                message = "$name success!",
                actionLabel = "Go to cart",
                duration = SnackbarDuration.Short,
                scaffoldState = scaffoldState,
                action = {
                    navController.navigate(Screens.CartScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true

                        restoreState = true
                    }
                },
                dismiss = {}
            )
        }
    }

    fun editItem(
        itemName: String,
        itemQuantity: String,
        ID: String,
        itemCreatedDate: Long,
        scaffoldState: ScaffoldState,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                if (itemName.isNotBlank() && itemQuantity.isNotBlank() &&
                    itemQuantity.toInt() > 0 && itemQuantity.toInt() < Int.MAX_VALUE && itemQuantity.isDigitsOnly()
                ) {
                    val item = ItemData(
                        name = itemName,
                        ID = ID,
                        quantity = itemQuantity.toInt(),
                        date = itemCreatedDate
                    )
                    itemList[currentSelectedItemIndex].name = itemName
                    itemList[currentSelectedItemIndex].quantity = itemQuantity.toInt()
                    updateItem(item)
                    showSuccessMessage(
                        name = "Update",
                        scaffoldState = scaffoldState,
                        navController = navController
                    )
                } else {
                    showSnackBarMessage("Invalid input(s)", scaffoldState = scaffoldState)
                }
            } catch (e: Exception) {
                showSnackBarMessage("Invalid input(s)", scaffoldState = scaffoldState)
            }
        }
    }

    fun deleteItemHelper() {
        val item = itemList[currentSelectedItemIndex]

        isItemDeleteSuccessful = true
        deletedItem = item.name
        removeItem(currentSelectedItemIndex)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteItem(item)
            }
        }
    }

    private suspend fun deleteItem(item: ItemData) {
        cartDatabase.itemDao().delete(item)
    }

    fun addRandomItem() {
        viewModelScope.launch(Dispatchers.Default) {
            for (i in 0 until 1_000) {
                val randomItemName = items[(0 until items.size).random()]
                val randomUUID = UUID.randomUUID().toString()
                val randomQuantity = (1..1_111).random()
                val item = ItemData(
                    randomItemName,
                    randomUUID,
                    randomQuantity,
                    System.currentTimeMillis()
                )
                Item.addItem(item)
                saveItem(item)
            }
        }
    }


    private suspend fun loadItems() {
        val items = cartDatabase.itemDao().getAll().toMutableStateList()
        itemList.addAll(items)
    }


    private suspend fun updateItem(item: ItemData) {
        cartDatabase.itemDao().update(item)
    }

    private suspend fun saveItem(item: ItemData) {
        cartDatabase.itemDao().insert(item)
    }


}