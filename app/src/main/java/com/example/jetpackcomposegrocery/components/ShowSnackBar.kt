package com.example.jetpackcomposegrocery.components

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import com.example.jetpackcomposegrocery.variable.Variable.isShowSnackbar

suspend fun showSnackbar(
    message: String,
    actionLabel: String?,
    duration: SnackbarDuration,
    scaffoldState: ScaffoldState,
    action: () -> Unit,
    dismiss: () -> Unit
) {
    if (isShowSnackbar) {
        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration
        )

        when (snackbarResult) {
            SnackbarResult.Dismissed -> {
                dismiss()
            }
            SnackbarResult.ActionPerformed -> {
                action()
            }
        }
        isShowSnackbar = false
    }
}