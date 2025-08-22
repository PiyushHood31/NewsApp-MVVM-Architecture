package com.piyushhhod.newsapp.ui.countries

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyushhhod.newsapp.data.repository.CountriesRepository
import com.piyushhhod.newsapp.ui.base.UiState
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountriesViewModel (private val countriesRepository: CountriesRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<String>>> (UiState.Loading)
   val uiState :StateFlow<UiState<List<String>>> = _uiState

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            Log.d("CountryViewModel", "Started fetching countries...")

            countriesRepository.getAllCountries(AppConstant.API_KEY)
                .catch { e ->
                    Log.e("CountryViewModel", "Error fetching countries: ${e.message}", e)
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect { countries ->
                    Log.d("CountryViewModel", "Fetched countries: $countries")
                    _uiState.value = UiState.Success(countries)
                }
        }
    }



}