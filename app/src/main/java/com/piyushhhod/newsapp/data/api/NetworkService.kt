package com.piyushhhod.newsapp.data.api

import com.piyushhhod.newsapp.data.model.NewsSourcesResponse
import com.piyushhhod.newsapp.data.model.TopHeadlineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String?,
        @Query("country") country: String?,
        @Query("language") language: String?,
        @Query("apiKey") apiKey: String
    ): TopHeadlineResponse

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String
    ): NewsSourcesResponse

    @GET("everything")
    suspend fun getEveryThing(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): TopHeadlineResponse


}