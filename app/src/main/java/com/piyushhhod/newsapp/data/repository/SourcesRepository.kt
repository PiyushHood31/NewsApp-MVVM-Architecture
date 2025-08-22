package com.piyushhhod.newsapp.data.repository

import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.data.model.SourceX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SourcesRepository @Inject constructor(private val networkService: NetworkService){

    fun getNewsSources(apiKey : String) : Flow<List<SourceX>>{
        return flow{
            emit(networkService.getSources(apiKey = apiKey))
        }.map {
            it.sources
        }

    }


}