package com.feevlic.justgraphql.presentation.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ConnectivitySnackBar(isConnected: Boolean, hostState: SnackbarHostState, modifier: Modifier) {
    val skipFirst = remember { mutableStateOf(true) }

    LaunchedEffect(isConnected) {
        if (skipFirst.value) {
            skipFirst.value = false
            return@LaunchedEffect
        }
        val message = "Connected: $isConnected"
        hostState.showSnackbar(message, duration = SnackbarDuration.Short)
    }
}