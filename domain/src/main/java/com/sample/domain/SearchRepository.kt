package com.sample.domain

import com.sample.domain.model.SearchDataState

/**
 * Search repository.
 * */
interface SearchRepository {

    /**
     * Gets the search data and converts it domain model.
     * Stores the query to shared preferences.
     * Builds [SearchDataState] for both success and error cases and returns it.

     * @return [SearchDataState] data.
     * */
    suspend fun getSearchData(query: String): SearchDataState

    /**
     * Gets query from the shared preferences if exists, or empty string.

     * @return [String] query.
     * */
    suspend fun getQuery(): String
}