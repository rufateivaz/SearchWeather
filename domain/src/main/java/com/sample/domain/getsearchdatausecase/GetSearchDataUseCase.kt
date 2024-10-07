package com.sample.domain.getsearchdatausecase

import com.sample.domain.SearchRepository
import com.sample.domain.model.SearchDataState
import javax.inject.Inject

/**
 * Use case to get the search result.
 *
 * @param repository search repository
 * */
class GetSearchDataUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    /**
     * Gets the search result.
     *
     * @param query is the search query to apply search operation for.
     *
     * @return [SearchDataState] states.
     * */
    suspend fun invoke(query: String): SearchDataState = repository.getSearchData(query)
}