package com.example.jetpackcomposegrocery.cart.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.cart.viewModel.CartViewModel
import com.example.jetpackcomposegrocery.item.Item.currentSelectedItemIndex
import com.example.jetpackcomposegrocery.item.Item.deletedItem
import com.example.jetpackcomposegrocery.item.Item.isEditItem
import com.example.jetpackcomposegrocery.item.Item.isItemDeleteSuccessful
import com.example.jetpackcomposegrocery.item.Item.itemList
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.variable.Variable.isShowSnackbar


@Composable
fun CartView(
    vm: CartViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    paddingValue: PaddingValues
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val haptic = LocalHapticFeedback.current
    var isEnableClick by remember { mutableStateOf(true) }

    val colorStops = arrayOf(
        0.95f to Color.Transparent,
        1f to MaterialTheme.colors.surface
    )

    LaunchedEffect(isItemDeleteSuccessful) {
        if (isItemDeleteSuccessful) {
            isShowSnackbar = true
            vm.showSnackbarMessage(message = "Deleted $deletedItem", scaffoldState = scaffoldState)
            isItemDeleteSuccessful = false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValue)
    ) {
        Column(modifier = Modifier.clickable {
            vm.addRandomItem()
        }) {
            Row(Modifier.fillMaxWidth()) {
                Label("Item")
                Label("ID")
                Label("Quantity")
            }
            Divider(
                Modifier
                    .fillMaxWidth(0.95f)
                    .padding(top = 8.dp)
                    .align(CenterHorizontally)
                    .background(Color.DarkGray),
                thickness = 1.dp

            )
        }

        Box {
            LazyColumn {
                items(itemList.size) { index ->

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(isEnableClick) {
                                    currentSelectedItemIndex = index
                                    isEditItem = true
                                    isEnableClick = false
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    navController.navigate(Screens.AddItemScreen.route)
                                    {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                    vm.setCurrentSelectedItem(index = index)
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .weight(0.33f)
                                    .padding(8.dp),
                            ) {

                                Text(
                                    text = itemList[index].name,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.primary,
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Text(
                                    text = vm.convertLongToDate(itemList[index].date),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Text(
                                text = itemList[index].ID,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.33f)
                                    .padding(8.dp)
                            )

                            Text(
                                text = vm.formatNumberWithCommas(itemList[index].quantity),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.33f)
                                    .padding(8.dp)
                            )
                        }
                        Divider(
                            Modifier
                                .fillMaxWidth(0.925f)
                                .align(CenterHorizontally)
                                .background(Color.DarkGray),
                            thickness = 0.5.dp
                        )

                        if (index == itemList.size - 1) {
                            Spacer(modifier = Modifier.padding(vertical = screenHeight * 0.10f))
                        }

                    }
                }
            }
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colorStops = colorStops
                        ),
                    )
                    .matchParentSize()
            )
        }

    }
}

@Composable
fun RowScope.Label(name: String) {
    Text(
        text = name,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.weight(0.33f)
    )
}