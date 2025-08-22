package com.piyushhhod.newsapp.ui.language

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyushhhod.newsapp.data.repository.LanguageRepository
import com.piyushhhod.newsapp.ui.base.UiState
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LanguageViewModel(private val languageRepository: LanguageRepository):ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<String>>> = _uiState

    init{
        fetchLanguageList()
    }

     private fun fetchLanguageList(){
         viewModelScope.launch {
             languageRepository.getAllLanguageList(AppConstant.API_KEY)
                 .catch { e ->
                     Log.d("LanguageViewModel", "In catch Block : ${e.message}")
                     _uiState.value = UiState.Error(e.toString())
                 }
                 .collect { language ->
                     Log.d("LanguageViewModel", "The Data is getting collected : ${language} ")
                     _uiState.value = UiState.Success(language)

                 }
         }
    }
}