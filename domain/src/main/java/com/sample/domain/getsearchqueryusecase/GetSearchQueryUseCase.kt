package com.sample.domain.getsearchqueryusecase

import com.sample.domain.SearchRepository
import javax.inject.Inject

class GetSearchQueryUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(): String = repository.getQuery()
}