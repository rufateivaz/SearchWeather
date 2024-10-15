package com.sample.data.search

import com.sample.data.di.IoDispatcher
import com.sample.data.search.extensions.toDomainModel
import com.sample.data.search.localdatasource.SearchPreferencesManager
import com.sample.data.search.remotedatasource.SearchAPIService
import com.sample.domain.search.SearchRepository
import com.sample.domain.search.model.SearchDataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * The repository implementation of [SearchRepository]
 *
 * @param apiService the api service for search operation.
 * @param preferencesManager the shared preference manager to store/get search query.
 */
class SearchRepositoryImpl @Inject constructor(
    private val apiService: SearchAPIService,
    private val preferencesManager: SearchPreferencesManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {

    /**
     * Gets the search data and converts it domain model.
     * Stores the query to shared preferences.
     * Builds [SearchDataState] for both success and error cases and returns it.

     * @return [SearchDataState] data.
     * */
    override suspend fun getSearchData(query: String): SearchDataState {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.getSearchResponse(query)
                if (response.isSuccessful) {
                    response.body()?.let {
                        preferencesManager.setQuery(query)
                        SearchDataState.Success(it.toDomainModel())
                    } ?: SearchDataState.Error(error = Exception("Response body is null"))
                } else {
                    SearchDataState.Error(error = Exception("Unsuccessful response: ${response.code()}"))
                }
            } catch (e: IOException) {
                SearchDataState.Error(error = Exception("Network error"))
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