package com.sample.domain.getsearchqueryusecase

import com.sample.domain.SearchRepository
import javax.inject.Inject

/**
 * Use case to get the stored search query.
 *
 * @param repository search repository
 * */
class GetSearchQueryUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    /**
     * Gets the stored search query.
     *
     * @return [String] query.
     * */
    suspend fun invoke(): String = repository.getQuery()
}