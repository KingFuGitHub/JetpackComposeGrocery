package com.example.jetpackcomposegrocery.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.navigation.NavDestination.Companion.hierarchy
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
    val items = remember { mutableListOf(Screens.CartScreen, Screens.ChartScreen) }

    AnimatedVisibility(
        visible = currentDestination?.route == Screens.CartScreen.route ||
                currentDestination?.route == Screens.ChartScreen.route,
//        visible = true,
        enter = slideInVertically(
//            initialOffsetY = { 200 },
            initialOffsetY = { 0 },
            animationSpec = tween(durationMillis = 100, easing = LinearEasing)
        ),
        exit = slideOutVertically(
//            targetOffsetY = { 200 },
            targetOffsetY = { 0 },
            animationSpec = tween(durationMillis = 100, easing = LinearEasing)
        ),
        content = {
            BottomNavigation(
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                for (screen in 0 until items.size) {
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == items[screen].route } == true
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
    )

}