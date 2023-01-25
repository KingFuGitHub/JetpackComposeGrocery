package com.example.jetpackcomposegrocery.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.item.Item.isEditItem
import com.example.jetpackcomposegrocery.navigation.Screens
import kotlinx.coroutines.CoroutineScope


@Composable
fun FloatingButton(
    currentDestination: NavDestination,
    scaffoldState: ScaffoldState,
    coroutine: CoroutineScope,
    haptic: HapticFeedback,
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = currentDestination.route == Screens.CartScreen.route,
        enter = slideInHorizontally(
            initialOffsetX = { 200 },
            animationSpec = tween(durationMillis = 100, easing = LinearEasing)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { 200 },
            animationSpec = tween(durationMillis = 100, easing = LinearEasing)
        ),
        content = {
            FloatingActionButton(
                onClick = {
                    isEditItem = false
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    navController.navigate(Screens.AddItemScreen.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
//                        restoreState = true
                    }
                },
                backgroundColor = MaterialTheme.colors.onSurface.copy(0.85f),
                contentColor = MaterialTheme.colors.surface,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    )
}