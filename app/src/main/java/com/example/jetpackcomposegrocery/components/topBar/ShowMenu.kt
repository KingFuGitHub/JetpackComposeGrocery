package com.example.jetpackcomposegrocery.components.topBar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.variable.Variable.showMenu

@Composable
fun ShowMenu(navController: NavHostController) {
    val isDarkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colors = MaterialTheme.colors.copy(secondaryVariant = if (isDarkTheme) Color.White else Color.Black),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    navController.navigate(Screens.SettingsScreen.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    showMenu = false
                }
            ) {
                Text(
                    text = Screens.SettingsScreen.name,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }

            DropdownMenuItem(onClick = { showMenu = false }) {
                Text(
                    text = "TBD",
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}