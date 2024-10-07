package com.sample.domain.getsearchqueryusecase

import com.sample.domain.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetSearchQueryUseCaseTest {

    private val repository: SearchRepository = mock()
    private lateinit var getSearchQueryUseCase: GetSearchQueryUseCase

    @Before
    fun setUp() {
        getSearchQueryUseCase = GetSearchQueryUseCase(repository)
    }

    @Test
    fun whenGettingSearchQueryGivenItExistsItShouldGetTheExistingQuery() = runTest {
        // Given
        val cityName = "cityName"
        whenever(repository.getQuery()).thenReturn(cityName)

        // When
        val result = getSearchQueryUseCase.invoke()

        // Then
        Assert.assertEquals(cityName, result)
    }

    @Test
    fun whenGettingSearchQueryGivenNotExistsItShouldGetEmptyString() = runTest {
        // Given
        whenever(repository.getQuery()).thenReturn("")

        // When
        val result = getSearchQueryUseCase.invoke()

        // Then
        Assert.assertTrue(result.isEmpty())
    }
}