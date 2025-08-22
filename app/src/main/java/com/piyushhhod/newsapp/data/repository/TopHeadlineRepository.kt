package com.piyushhhod.newsapp.data.repository

import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.data.model.Article
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(
        country: String? = AppConstant.COUNTRY,
        language: String? = null,
        source: String? = null,
        apiKey: String
    ): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(
                country = country,
                language = language,
                sources = source,
                apiKey = apiKey
            ))
        }.map { it.articles }
    }
}