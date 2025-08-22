package com.piyushhhod.newsapp.data.repository

import android.util.Log
import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageRepository @Inject constructor(private val networkService: NetworkService){

    fun getAllLanguageList(apiKey : String) : Flow<List<String>>{
        return flow {
          val response = networkService.getSources(apiKey)
            emit(response)
        }.map {response ->
            val language = response.sources.map{it.language}.distinct()
            val languageName = language.map{code ->
                AppConstant.languageCodeToNameMap[code.lowercase()] ?: code.uppercase()
            }.sorted()
            Log.d("LanguageRepository","Mapped Language : $languageName")
            languageName

        }
    }
}