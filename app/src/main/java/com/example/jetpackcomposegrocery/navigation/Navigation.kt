package com.example.jetpackcomposegrocery.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcomposegrocery.cart.view.AddItemView
import com.example.jetpackcomposegrocery.cart.view.CartView
import com.example.jetpackcomposegrocery.cart.viewModel.CartViewModel
import com.example.jetpackcomposegrocery.chart.view.ChartView
import com.example.jetpackcomposegrocery.chart.viewModel.ChartViewModel
import com.example.jetpackcomposegrocery.settings.view.SettingsView
import com.example.jetpackcomposegrocery.settings.viewModel.SettingsViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    cartViewModel: CartViewModel,
    chartViewModel: ChartViewModel,
    settingsViewModel: SettingsViewModel,
    paddingValues: PaddingValues

) {
    NavHost(
        navController = navController,
        startDestination = Screens.CartScreen.route,
    ) {
        composable(Screens.CartScreen.route) {
            CartView(
                navController = navController,
                scaffoldState = scaffoldState,
                vm = cartViewModel,
                paddingValue = paddingValues
            )
        }

        composable(Screens.ChartScreen.route) {
            ChartView(
                navController = navController,
                scaffoldState = scaffoldState,
                vm = chartViewModel,
                paddingValue = paddingValues
            )
        }

        composable(Screens.SettingsScreen.route) {
            SettingsView(
                navController = navController,
                scaffoldState = scaffoldState,
                vm = settingsViewModel,
                paddingValue = paddingValues
            )
        }

        composable(Screens.AddItemScreen.route) {
            AddItemView(
                paddingValues = paddingValues,
                vm = cartViewModel,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }

    }
}