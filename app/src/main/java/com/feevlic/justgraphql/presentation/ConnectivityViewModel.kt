package com.feevlic.justgraphql.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feevlic.justgraphql.domain.ObserveConnectivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(private val observeConnectivity: ObserveConnectivityUseCase) :
    ViewModel() {


    val isconnected = observeConnectivity()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    private var initialized = false

    init {
        viewModelScope.launch {
            isconnected.collect { connected ->
                if (!initialized) {
                    initialized = true
                    return@collect
                }
                val message = if (connected) "Back online" else "You are offline"
                _events.emit(message)
            }
        }
    }
}