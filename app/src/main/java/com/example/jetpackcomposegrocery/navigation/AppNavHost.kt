package com.example.jetpackcomposegrocery.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposegrocery.cart.viewModel.CartFactory
import com.example.jetpackcomposegrocery.cart.viewModel.CartViewModel
import com.example.jetpackcomposegrocery.chart.viewModel.ChartFactory
import com.example.jetpackcomposegrocery.chart.viewModel.ChartViewModel
import com.example.jetpackcomposegrocery.components.BottomBar
import com.example.jetpackcomposegrocery.components.FloatingButton
import com.example.jetpackcomposegrocery.components.topBar.TopBar
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.CartPreferences
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.ChartPreferences
import com.example.jetpackcomposegrocery.repository.preferencesDataStore.SettingsPreferences
import com.example.jetpackcomposegrocery.repository.room.ItemDatabase
import com.example.jetpackcomposegrocery.settings.viewModel.SettingsFactory
import com.example.jetpackcomposegrocery.settings.viewModel.SettingsViewModel


@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val haptic: HapticFeedback = LocalHapticFeedback.current

    val cartViewModel: CartViewModel = viewModel(
        factory = CartFactory(
            cartPreferences = CartPreferences.getInstance(context = context),
            cartDatabase = ItemDatabase.getInstance(context = context)
        )
    )

    val chartViewModel: ChartViewModel = viewModel(
        factory = ChartFactory(chartPreferences = ChartPreferences.getInstance(context = context))
    )

    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsFactory(settingsPreferences = SettingsPreferences.getInstance(context = context))
    )

    LaunchedEffect(Unit) {
        settingsViewModel.loadSettingsIsDarkTheme()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentDestination != null) {
                TopBar(navController = navController, currentDestination = currentDestination)
            }
        },
        bottomBar = {
            BottomBar(currentDestination = currentDestination, navController = navController)
        },
        floatingActionButton = {
            if (currentDestination != null) {
                FloatingButton(
                    currentDestination = currentDestination,
                    scaffoldState = scaffoldState,
                    coroutine = coroutine,
                    haptic = haptic,
                    navController = navController
                )

            }
        },
        content = { paddingValues ->
            Navigation(
                navController = navController,
                scaffoldState = scaffoldState,
                cartViewModel = cartViewModel,
                chartViewModel = chartViewModel,
                settingsViewModel = settingsViewModel,
                paddingValues = paddingValues
            )
        }
    )
}