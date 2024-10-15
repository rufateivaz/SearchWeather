package com.sample.weather.feature.search

import com.sample.domain.search.getsearchdatausecase.GetSearchDataUseCase
import com.sample.domain.search.getsearchqueryusecase.GetSearchQueryUseCase
import com.sample.domain.search.model.SearchData
import com.sample.domain.search.model.SearchDataState
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {
    private lateinit var vm: SearchViewModel
    private val getSearchDataUseCase: GetSearchDataUseCase = mock()
    private val getSearchQueryUseCase: GetSearchQueryUseCase = mock()

    @Test
    fun whenGettingSearchResultGivenSuccessItShouldUpdateUIWithSuccessState() = runTest {
        // Given
        val state = SearchDataState.Success(_searchData)
        whenever(getSearchDataUseCase.invoke(any())).thenReturn(state)

        // When
        val testDispatcher = StandardTestDispatcher(testScheduler)
        vm = SearchViewModel(getSearchDataUseCase, getSearchQueryUseCase, testDispatcher)
        vm.getSearchData("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        Assert.assertEquals(true, vm.initialSearchResultPresented)
        Assert.assertEquals(state, vm.searchDataState.value)
    }

    @Test
    fun whenGettingSearchResultGivenErrorItShouldUpdateUIWithErrorState() = runTest {
        // Given
        val exception = Exception()
        val state = SearchDataState.Error(exception)
        whenever(getSearchDataUseCase.invoke(any())).thenReturn(state)

        // When
        val testDispatcher = StandardTestDispatcher(testScheduler)
        vm = SearchViewModel(getSearchDataUseCase, getSearchQueryUseCase, testDispatcher)
        vm.getSearchData("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        Assert.assertEquals(true, vm.initialSearchResultPresented)
        Assert.assertEquals(state, vm.searchDataState.value)
    }

    @Test
    fun whenGettingOldQueryGivenEmptyItShouldGetInitStateAndFlag() = runTest {
        // Given
        whenever(getSearchQueryUseCase.invoke()).thenReturn("")

        // When
        val testDispatcher = StandardTestDispatcher(testScheduler)
        vm = SearchViewModel(getSearchDataUseCase, getSearchQueryUseCase, testDispatcher)
        vm.getSearchDataWithOldQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        Assert.assertEquals(true, vm.initialSearchResultPresented)
        Assert.assertEquals(SearchDataState.Idle, vm.searchDataState.value)
    }

    @Test
    fun whenGettingOldQueryGivenQueryItShouldPerformSearchWithQuery() = runTest {
        // Given
        whenever(getSearchQueryUseCase.invoke()).thenReturn("_")
        whenever(getSearchDataUseCase.invoke(any())).thenReturn(SearchDataState.Success(_searchData))

        // When
        val testDispatcher = StandardTestDispatcher(testScheduler)
        vm = SearchViewModel(getSearchDataUseCase, getSearchQueryUseCase, testDispatcher)
        vm.getSearchDataWithOldQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        Assert.assertEquals(true, vm.initialSearchResultPresented)
        verify(getSearchDataUseCase).invoke("_")
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