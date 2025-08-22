package com.piyushhhod.newsapp.data.repository

import android.util.Log
import com.piyushhhod.newsapp.data.api.NetworkService
import com.piyushhhod.newsapp.utils.AppConstant.countryCodeToNameMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountriesRepository @Inject constructor(private val networkService: NetworkService) {

    fun getAllCountries(apiKey: String): Flow<List<String>> {
        return flow {
            Log.d("CountryRepository", "Calling networkService.getSources()")
            val response = networkService.getSources(apiKey = apiKey)
            Log.d("CountryRepository", "Received response with ${response.sources.size} sources")
            emit(response)
        }.map { response ->
            val countries = response.sources.map { it.country }.distinct()
            val mappedCountries = countries.map { code ->
                countryCodeToNameMap[code.lowercase()] ?: code.uppercase()
            }.sorted()
            Log.d("CountryRepository", "Mapped countries: $mappedCountries")
            mappedCountries

        }
    }

}