package com.example.jetpackcomposegrocery.chart.view

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposegrocery.chart.viewModel.ChartViewModel


@Composable
fun ChartView(
    vm: ChartViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {

    LaunchedEffect(Unit) {
        vm.totalOfQuantity()
        vm.sumOfQuantity()
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val configurationOrientation = LocalConfiguration.current.orientation

        val animatedProcess by animateFloatAsState(
            targetValue = vm.currentQuantityPercentage,
            animationSpec = tween(
                durationMillis = 1,
                easing = LinearEasing
            )
        )

        val animatedCircularProgressBarColor by animateColorAsState(
            targetValue = MaterialTheme.colors.primary.copy(0.75f),
            animationSpec = tween(
                durationMillis = 1,
                easing = LinearEasing
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    Canvas(
                        modifier = Modifier
                            .padding(bottom = 350.dp)
                            .size(300.dp, 300.dp)
                    ) {
                        drawArc(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    animatedCircularProgressBarColor,
                                    Color.Blue.copy(0.75f),
                                )
                            ),
                            startAngle = -90f,
                            sweepAngle = 360 * animatedProcess,
                            useCenter = false,
                            style = Stroke(20f, cap = StrokeCap.Round)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 350.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Total Quantity:",
                            fontSize = 24.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        Text(
                            text = vm.sumQuantity.toInt().toString(),
                            fontSize = 24.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
            }
        }


    }
}