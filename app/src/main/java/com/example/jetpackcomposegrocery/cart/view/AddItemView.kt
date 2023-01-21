package com.example.jetpackcomposegrocery.cart.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.cart.viewModel.CartViewModel
import com.example.jetpackcomposegrocery.item.Item.currentSelectedItemIndex
import com.example.jetpackcomposegrocery.item.Item.isEditItem
import com.example.jetpackcomposegrocery.item.Item.itemList
import com.example.jetpackcomposegrocery.navigation.Screens
import com.example.jetpackcomposegrocery.variable.Variable.isShowSnackbar
import com.example.jetpackcomposegrocery.variable.Variable.resetInputText


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddItemView(
    paddingValues: PaddingValues,
    vm: CartViewModel,
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {
    val haptic = LocalHapticFeedback.current
    var itemName by rememberSaveable { mutableStateOf(if (isEditItem) itemList[currentSelectedItemIndex].name else "") }
    var itemID by rememberSaveable { mutableStateOf(if (isEditItem) itemList[currentSelectedItemIndex].ID else "") }
    var itemQuantity by rememberSaveable { mutableStateOf(if (isEditItem) itemList[currentSelectedItemIndex].quantity.toString() else "") }
    val itemCreatedDate by rememberSaveable { mutableStateOf(if (itemList.size > 0) itemList[currentSelectedItemIndex].date else 0L) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState: ScrollState = rememberScrollState()


    LaunchedEffect(resetInputText) {
        if (resetInputText) {
            itemName = ""
            itemID = ""
            itemQuantity = ""
            resetInputText = false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            singleLine = true,
            shape = RoundedCornerShape(50),
            value = itemName,
            onValueChange = { itemName = it },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            label = { Text("Name", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
            ),
        )


        OutlinedTextField(
            readOnly = isEditItem,
            modifier = Modifier.fillMaxWidth(0.8f),
            singleLine = true,
            shape = RoundedCornerShape(50),
            value = itemID,
            onValueChange = { itemID = it },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            label = { Text("ID", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            singleLine = true,
            shape = RoundedCornerShape(50),
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            label = { Text("Quantity", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        )


        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                isShowSnackbar = true
                if (isEditItem) {
                    if (itemName == itemList[currentSelectedItemIndex].name &&
                        itemQuantity == itemList[currentSelectedItemIndex].quantity.toString()
                    ) {
                        return@Button
                    }
                    vm.editItem(
                        itemName = itemName,
                        itemQuantity = itemQuantity,
                        ID = itemID,
                        itemCreatedDate = itemCreatedDate,
                        scaffoldState = scaffoldState,
                        navController = navController
                    )
                } else {
                    vm.addToCart(
                        itemName = itemName,
                        itemID = itemID,
                        itemQuantity = itemQuantity,
                        scaffoldState = scaffoldState,
                        navController = navController
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 7.dp),
            shape = RoundedCornerShape(50.dp),
            elevation = ButtonDefaults.elevation(0.dp)

        ) {
            Text(
                text = if (isEditItem) "Update item" else "Add to cart",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 7.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (isEditItem) {
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    vm.deleteItem()
                    navController.navigate(Screens.CartScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 7.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                elevation = ButtonDefaults.elevation(0.dp)

            ) {
                Text(
                    text = "Delete item",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 7.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}
