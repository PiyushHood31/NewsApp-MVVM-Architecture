package com.piyushhhod.newsapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyushhhod.newsapp.data.model.Article
import com.piyushhhod.newsapp.data.repository.SearchRepository
import com.piyushhhod.newsapp.ui.base.UiState
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private val queryFlow = MutableSharedFlow<String>(replay = 1)

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            queryFlow
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    searchRepository.getSearchResult(query, AppConstant.API_KEY)
                        .onStart { _uiState.value = UiState.Loading }
                        .catch { e -> _uiState.value = UiState.Error(e.message ?: "Unknown error") }
                }
                .collect { articles ->
                    _uiState.value = UiState.Success(articles)
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }
}
