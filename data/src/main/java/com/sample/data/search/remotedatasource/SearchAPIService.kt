package com.sample.data.search.remotedatasource

import com.sample.data.BuildConfig
import com.sample.data.search.remotedatasource.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api service for the search operation.
 * */
interface SearchAPIService {
    /**
     * Api service to perform the search request.
     *
     * @param query is the city name given as search query.
     * @param appId is the API key.
     *
     * @return [SearchResponse] data.
     * */
    @GET("weather")
    suspend fun getSearchResponse(
        @Query("q") query: String,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): Response<SearchResponse>
}