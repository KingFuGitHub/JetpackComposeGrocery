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
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.CartPreferences
import com.example.jetpackcomposegrocery.repository.room.ItemData
import com.example.jetpackcomposegrocery.repository.room.ItemDatabase
import com.example.jetpackcomposegrocery.repository.room.ItemRepository
import com.example.jetpackcomposegrocery.variable.Variable.resetInputText
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
            loadListItem()
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
                        showSnackbarMessage(
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
                    showSnackbarMessage("Invalid input(s)", scaffoldState = scaffoldState)
                }
            } catch (e: java.lang.Exception) {
                showSnackbarMessage("Invalid input(s)", scaffoldState = scaffoldState)
            }
        }
    }

    private fun showSnackbarMessage(message: String, scaffoldState: ScaffoldState) {
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
                    updateListItem(item)
                    showSuccessMessage(
                        name = "Update",
                        scaffoldState = scaffoldState,
                        navController = navController
                    )
                } else {
                    showSnackbarMessage("Invalid input(s)", scaffoldState = scaffoldState)
                }
            } catch (e: Exception) {
                showSnackbarMessage("Invalid input(s)", scaffoldState = scaffoldState)
            }
        }
    }

    fun deleteItem() {
        isItemDeleteSuccessful = true
        deletedItem = itemList[currentSelectedItemIndex].name
        viewModelScope.launch {
            ItemRepository(cartDatabase.itemDao()).deleteItem(
                itemList[currentSelectedItemIndex]
            )
        }
        Item.removeItem(currentSelectedItemIndex)
    }

    fun addRandomItem() {
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
        saveListItem(item)
    }

    private suspend fun loadListItem() {
        val items = ItemRepository(cartDatabase.itemDao()).readAllData.first().toMutableStateList()
        Item.loadItems(items)
    }

    private fun saveListItem(item: ItemData) {
        viewModelScope.launch {
            ItemRepository(cartDatabase.itemDao()).addItem(item)
        }
    }

    private fun updateListItem(item: ItemData) {
        viewModelScope.launch {
            ItemRepository(cartDatabase.itemDao()).updateItem(item)
        }
    }

    private fun saveItem(item: ItemData) {
        viewModelScope.launch {
            ItemRepository(cartDatabase.itemDao()).addItem(item)
        }
    }


}