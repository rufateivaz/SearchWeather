package com.sample.domain.getsearchdatausecase

import com.sample.domain.SearchRepository
import com.sample.domain.model.SearchData
import com.sample.domain.model.SearchDataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetSearchDataUseCaseTest {

    private val repository: SearchRepository = mock()
    private lateinit var getSearchDataUseCase: GetSearchDataUseCase

    @Before
    fun setUp() {
        getSearchDataUseCase = GetSearchDataUseCase(repository)
    }

    @Test
    fun whenGettingSearchDataGivenSuccessItShouldGet() = runTest {
        // Given
        val state = SearchDataState.Success(_searchData)
        whenever(repository.getSearchData(any())).thenReturn(state)

        // When
        val result = getSearchDataUseCase.invoke("")

        // Then
        Assert.assertEquals(state, result)
    }

    @Test
    fun whenGettingSearchDataGivenErrorTheItShouldGetError() = runTest {
        // Given
        val exception = Exception()
        val state = SearchDataState.Error(exception)
        whenever(repository.getSearchData(any())).thenReturn(state)

        // When
        val result = getSearchDataUseCase.invoke("")

        // Then
        Assert.assertEquals(state, result)
    }

    private val _searchData = SearchData(
        city = "city",
        temperature = 0,
        lowTemp = -1,
        highTemp = 1,
        feelsLike = 1,
        icon = "icon",
        description = "description",
        humidity = 0
    )

}