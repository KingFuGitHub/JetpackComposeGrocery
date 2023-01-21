package com.example.jetpackcomposegrocery.components.topBar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.R
import com.example.jetpackcomposegrocery.item.Item.currentSelectedItemIndex
import com.example.jetpackcomposegrocery.item.Item.isEditItem
import com.example.jetpackcomposegrocery.item.Item.itemList
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.variable.Variable.showMenu


@Composable
fun TopBar(navController: NavHostController, currentDestination: NavDestination) {
    TopAppBar(
        title = {
            Text(
                text = when (currentDestination.route) {
                    Screens.SettingsScreen.route -> {
                        Screens.SettingsScreen.name
                    }
                    Screens.AddItemScreen.route -> {
                        if(isEditItem) itemList[currentSelectedItemIndex].name else Screens.AddItemScreen.name
                    }
                    else -> {
                        ""
                    }
                },
                maxLines = 1, overflow = TextOverflow.Ellipsis,
            )
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            if (navController.previousBackStackEntry != null && navController.currentDestination?.route != Screens.CartScreen.route &&
                navController.currentDestination?.route != Screens.ChartScreen.route
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_new_24),
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (navController.currentDestination?.route == Screens.CartScreen.route ||
                navController.currentDestination?.route == Screens.ChartScreen.route
            ) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                ShowMenu(navController = navController)
            }
        }
    )
}