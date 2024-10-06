package com.sample.data.remotedatasource

import com.sample.data.BuildConfig
import com.sample.data.remotedatasource.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPIService {
    @GET("weather")
    suspend fun getSearchResponse(
        @Query("q") query: String,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): SearchResponse
}