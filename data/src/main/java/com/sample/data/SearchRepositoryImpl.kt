package com.sample.data

import com.sample.data.extensions.toDomainModel
import com.sample.data.localdatasource.SearchPreferencesManager
import com.sample.data.remotedatasource.SearchAPIService
import com.sample.domain.SearchRepository
import com.sample.domain.model.SearchDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(
    private val apiService: SearchAPIService,
    private val preferencesManager: SearchPreferencesManager
) : SearchRepository {

    override suspend fun getSearchData(query: String): SearchDataState {
        return withContext(Dispatchers.IO) {
            try {
                val searchData = apiService.getSearchResponse(query).toDomainModel()
                preferencesManager.setQuery(query)
                SearchDataState.Success(searchData)
            }
            catch (e: Exception) {
                SearchDataState.Error(e)
            }
        }
    }

    override suspend fun getQuery(): String = withContext(Dispatchers.IO) {
        preferencesManager.getQuery() ?: ""
    }
}