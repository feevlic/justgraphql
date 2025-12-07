package com.feevlic.justgraphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.feevlic.justgraphql.presentation.ConnectivityViewModel
import com.feevlic.justgraphql.presentation.CountriesScreen
import com.feevlic.justgraphql.presentation.CountriesViewModel
import com.feevlic.justgraphql.ui.theme.JustgraphqlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustgraphqlTheme {
                val countriesViewmodel = hiltViewModel<CountriesViewModel>()
                val countriesState by countriesViewmodel.state.collectAsState()
                val connectivityViewmodel = hiltViewModel<ConnectivityViewModel>()

                val snackBarHostState = remember { SnackbarHostState() }

                LaunchedEffect(connectivityViewmodel.events) {
                    connectivityViewmodel.events.collect { message ->
                        snackBarHostState.showSnackbar(
                            message
                        )
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        CountriesScreen(
                            state = countriesState,
                            onSelectCountry = countriesViewmodel::selectCountry,
                            onDismissCountryDialog = countriesViewmodel::dismissCountryDialog
                        )
                        // You can also show a persistent banner using `connectivityState` if desired.
                    }
                }
            }
        }
    }
}

