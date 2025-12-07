package com.feevlic.justgraphql.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feevlic.justgraphql.domain.DetailedCountry
import com.feevlic.justgraphql.domain.GetCountriesUseCase
import com.feevlic.justgraphql.domain.GetCountryUseCase
import com.feevlic.justgraphql.domain.SimpleCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountryUseCase: GetCountryUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    data class CountriesState(
        val countries: List<SimpleCountry> = emptyList(),
        val isLoading: Boolean = false,
        val selectedCountry: DetailedCountry? = null
    )

    private val _state = MutableStateFlow(CountriesState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            try {
                val countries = getCountriesUseCase.execute()
                _state.update { it.copy(countries = countries, isLoading = false) }
            } catch (t: Throwable) {
                _state.update { it.copy(isLoading = false) }
                _events.emit("Failed to load countries Please check your Network")
            }
        }
    }

    fun selectCountry(code: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        selectedCountry = getCountryUseCase.execute(code)
                    )
                }
            } catch (t: Throwable) {
                _events.emit("failed to load country details")
            }
        }
    }

    fun dismissCountryDialog() {
        _state.update {
            it.copy(
                selectedCountry = null
            )
        }
    }
}