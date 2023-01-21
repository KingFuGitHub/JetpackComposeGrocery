package com.example.jetpackcomposegrocery.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcomposegrocery.repository.room.ItemData

object Item {

    val itemList = mutableStateListOf<ItemData>()
    var currentSelectedItemIndex by mutableStateOf(0)
    var isEditItem by mutableStateOf(false)
    var isItemDeleteSuccessful by mutableStateOf(false)
    var deletedItem by mutableStateOf("")

    fun addItem(itemData: ItemData) {
        itemList.add(0, itemData)
    }

    fun removeItem(index: Int) {
        itemList.removeAt(index)
    }

    fun loadItems(items: SnapshotStateList<ItemData>) {
        itemList.addAll(items)
    }

    val items = mutableListOf(
        "Strawberry festival hot dog",
        "White rice",
        "Brown rice",
        "All-purpose flour",
        "Rainbow lollipop",
        "Dark chocolate Kit Kat",
        "Sparking water",
        "California sushi roll",
        "White Cheese",
        "Cooking oil",
        "Fat-free milk",
        "Fresh garlic",
        "Gatorade",
        "Chopped tomatoes",
        "Premium dog food",
        "Big bag cat food",
        "Costco Grilled Chicken",
        "Dry peanut",
        "Pink salt",
        "Organic honey",
        "Orange juice",
        "Ground beef",
        "Yellow Banana",
        "High quality vinegar",
        "Sliced cheese",
        "Sugar cubes",
        "Red cherry",
        "Creamy butter",
        "Yellow Banana",
        "Strawberry",
        "Refreshing waterlemon",
        "Red peper",
        "Pork belly",
        "BBQ steak",
        "Hot dogs",
        "Krispy Kreme donut",
        "Baguette",
        "Condensed milk",
        "Refreshing Yogurt",
        "Green Basil",
        "White beans",
        "Cooked Salmon",
        "Fresh tuna",
        "skinless white meant",
        "Baking powder",
        "Baking soda",
        "Dry Yeast",
        "Green Avocado",
        "Sweet apple",
        "Salad dressing",
        "White wine vinegar",
        "Crushed red pepper",
        "Garlic powder",
        "Breadcrumbs",
        "Crispy bacon",
        "Crunchy shrimp",
        "Sweet corn",
        "Peanut butter",
        "Shredded cheese",
        "Spicy chicken",
        "Organic grape juice"
    )
}