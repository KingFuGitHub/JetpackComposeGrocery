package com.example.jetpackcomposegrocery.settings.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.BuildConfig
import com.example.jetpackcomposegrocery.settings.viewModel.SettingsViewModel
import com.example.jetpackcomposegrocery.util.NoRippleTheme
import com.example.jetpackcomposegrocery.variable.Variable.isDarkTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SettingsView(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    vm: SettingsViewModel
) {
    val scrollState: ScrollState = rememberScrollState()
    val haptic = LocalHapticFeedback.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        Text(
            text = "Theme",
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp)
        )

        Card(
            modifier = Modifier.padding(5.dp),
            shape = RoundedCornerShape(30.dp),
            backgroundColor = MaterialTheme.colors.primary.copy(0.2f),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        isDarkTheme = !isDarkTheme
                        coroutineScope.launch {
                            vm.saveSettingsIsDarkTheme()
                        }
                    }
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark Theme",
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = if (isDarkTheme) Color.White else Color.Black
                )

                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            isDarkTheme = !isDarkTheme
                            coroutineScope.launch {
                                vm.saveSettingsIsDarkTheme()
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                        ),
                    )
                }
            }
        }

        Text(
            text = "About App",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )

        Card(
            modifier = Modifier.padding(5.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary.copy(0.2f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Current Version ${BuildConfig.VERSION_NAME}",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = "Made using Kotlin and Jetpack Compose",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onBackground.copy(0.75f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }



}