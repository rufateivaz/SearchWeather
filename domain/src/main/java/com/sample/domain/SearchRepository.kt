package com.sample.domain

import com.sample.domain.model.SearchDataState

interface SearchRepository {
    suspend fun getSearchData(query: String): SearchDataState
    suspend fun getQuery(): String
}