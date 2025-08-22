package com.piyushhhod.newsapp.data.repository

import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(private val networkService: NetworkService) {

    fun getSearchResult(query: String, apikey: String): Flow<List<Article>> {

        return flow {
            emit(networkService.getEveryThing(q = query, apiKey = apikey))

        }.map {
            it.articles
        }

    }
}