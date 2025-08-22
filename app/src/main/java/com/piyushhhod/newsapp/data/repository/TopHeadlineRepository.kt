package com.piyushhhod.newsapp.data.repository

import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country : String , apiKey : String) : Flow<List<Article>>{
        return flow{
            emit(networkService.getTopHeadlines(country = country , apiKey = apiKey))
        }.map {
            it.articles
        }
    }
}