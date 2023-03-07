package com.example.jetpackcomposegrocery.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.util.NoRippleTheme
import com.example.jetpackcomposegrocery.variable.Variable.isDarkTheme


@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val items = remember { listOf(Screens.CartScreen, Screens.ChartScreen) }

    BottomNavigation(
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        for (screen in items.indices) {
            val selected = currentDestination?.route == items[screen].route

            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                BottomNavigationItem(
                    selectedContentColor = if (isDarkTheme) Color.White else Color.Black,
                    unselectedContentColor = if (isDarkTheme) Color.White else Color.Black,
                    icon = {
                        Icon(
                            painterResource(if (selected) items[screen].filledIconId!! else items[screen].outlinedIconId!!),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            items[screen].name,
                            fontSize = 10.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(items[screen].route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }


}