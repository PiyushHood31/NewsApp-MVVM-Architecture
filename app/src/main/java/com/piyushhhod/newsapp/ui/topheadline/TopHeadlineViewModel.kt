package com.piyushhhod.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyushhhod.newsapp.data.model.Article
import com.piyushhhod.newsapp.data.repository.TopHeadlineRepository
import com.piyushhhod.newsapp.ui.base.UiState
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class TopHeadlineViewModel(private val topHeadlineRepository: TopHeadlineRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Article>>> = _uiState



     fun fetchNews(language: String?, source: String?, country: String?) {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlines(
                source = source,
                language = language,
                country = country,
                apiKey = AppConstant.API_KEY
            )
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}