package com.sample.data

import com.sample.data.extensions.toDomainModel
import com.sample.data.localdatasource.SearchPreferencesManager
import com.sample.data.remotedatasource.SearchAPIService
import com.sample.domain.SearchRepository
import com.sample.domain.model.SearchDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The repository implementation of [SearchRepository]
 *
 * @param apiService the api service for search operation.
 * @param preferencesManager the shared preference manager to store/get search query.
 */
class SearchRepositoryImpl(
    private val apiService: SearchAPIService,
    private val preferencesManager: SearchPreferencesManager
) : SearchRepository {

    /**
     * Gets the search data and converts it domain model.
     * Stores the query to shared preferences.
     * Builds [SearchDataState] for both success and error cases and returns it.

     * @return [SearchDataState] data.
     * */
    override suspend fun getSearchData(query: String): SearchDataState {
        return withContext(Dispatchers.IO) {
            try {
                val searchData = apiService.getSearchResponse(query).toDomainModel()
                preferencesManager.setQuery(query)
                SearchDataState.Success(searchData)
            } catch (e: Exception) {
                SearchDataState.Error(e)
            }
        }
    }

    /**
     * Gets query from the shared preferences if exists, or empty string.

     * @return [String] query.
     * */
    override suspend fun getQuery(): String = withContext(Dispatchers.IO) {
        preferencesManager.getQuery() ?: ""
    }
}