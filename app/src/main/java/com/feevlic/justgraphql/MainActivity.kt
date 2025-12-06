package com.feevlic.justgraphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
                val viewmodel = hiltViewModel<CountriesViewModel>()
                val state by viewmodel.state.collectAsState()
                CountriesScreen(
                    state = state,
                    onSelectCountry = viewmodel::selectCountry,
                    onDismissCountryDialog = viewmodel::dismissCountryDialog
                )
            }
        }
    }
}

