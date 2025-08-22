package com.piyushhhod.newsapp.ui.news_sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyushhhod.newsapp.data.model.SourceX
import com.piyushhhod.newsapp.data.repository.SourcesRepository
import com.piyushhhod.newsapp.ui.base.UiState
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsSourcesViewModel (private val newsSourcesRepository: SourcesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<SourceX>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<SourceX>>> = _uiState

    init{
        fetchSources()
    }

    private fun fetchSources(){
        viewModelScope.launch {
            newsSourcesRepository.getNewsSources(AppConstant.API_KEY)
                .catch { e->
                    _uiState.value = UiState.Error(e.toString())
                }.collect{
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}
