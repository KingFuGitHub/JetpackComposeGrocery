package com.example.jetpackcomposegrocery.navigation

import com.example.jetpackcomposegrocery.R

sealed class Screens(
    val route: String,
    val name: String,
    val filledIconId: Int?,
    val outlinedIconId: Int?
) {
    object CartScreen : Screens(
        route = "cart",
        name = "Cart",
        filledIconId = R.drawable.ic_filled_grocery_store_24,
        outlinedIconId = R.drawable.ic_outline_grocery_store_24
    )

    object ChartScreen : Screens(
        route = "chart",
        name = "Chart",
        filledIconId = R.drawable.ic_filled_insert_chart_24,
        outlinedIconId = R.drawable.ic_outline_insert_chart_24
    )

    object SettingsScreen : Screens(
        route = "settings",
        name = "Settings",
        filledIconId = null,
        outlinedIconId = null
    )

    object AddItemScreen : Screens(
        route = "addItemScreen",
        name = "Add Item",
        filledIconId = null,
        outlinedIconId = null
    )
}

