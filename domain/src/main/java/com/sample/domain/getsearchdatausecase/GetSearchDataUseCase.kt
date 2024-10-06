package com.sample.domain.getsearchdatausecase

import com.sample.domain.SearchRepository
import com.sample.domain.model.SearchDataState
import javax.inject.Inject

class GetSearchDataUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(query: String): SearchDataState = repository.getSearchData(query)
}