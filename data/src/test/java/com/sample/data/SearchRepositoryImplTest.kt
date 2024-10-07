package com.sample.data

import com.sample.data.localdatasource.SearchPreferencesManager
import com.sample.data.remotedatasource.SearchAPIService
import com.sample.data.remotedatasource.model.Main
import com.sample.data.remotedatasource.model.SearchResponse
import com.sample.data.remotedatasource.model.Weather
import com.sample.domain.model.SearchData
import com.sample.domain.model.SearchDataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryImplTest {

    private lateinit var searchRepository: SearchRepositoryImpl
    private val searchPreferencesManager: SearchPreferencesManager = mock()
    private val apiService: SearchAPIService = mock()

    @Before
    fun setUp() {
        searchRepository = SearchRepositoryImpl(apiService, searchPreferencesManager)
    }

    @Test
    fun whenGettingSearchDataGivenSuccessThenItShouldReturnSuccessState() = runTest {
        // Given
        whenever(apiService.getSearchResponse(any(), any())).thenReturn(_response)
        whenever(searchPreferencesManager.setQuery(any())).then {}

        // When
        val result = searchRepository.getSearchData("")

        // Then
        Assert.assertEquals(SearchDataState.Success(_searchData), result)
        verify(searchPreferencesManager).setQuery("")
    }

    @Test
    fun whenGettingSearchDataGivenErrorThenItShouldReturnErrorState() = runTest {
        // Given
        val exception = Exception()
        whenever(apiService.getSearchResponse(any(), any())).then { throw exception }

        // When
        val result = searchRepository.getSearchData("")

        // Then
        Assert.assertEquals(SearchDataState.Error(exception), result)
    }

    @Test
    fun whenGettingQueryGivenExistItShouldGetQuery() = runTest {
        // Given
        val query = "oldQuery"
        whenever(searchPreferencesManager.getQuery()).thenReturn(query)
        // When
        val result = searchRepository.getQuery()

        // Then
        Assert.assertEquals(query, result)
    }

    @Test
    fun whenGettingQueryGivenNotExistItShouldGetEmptyString() = runTest {
        // Given
        whenever(searchPreferencesManager.getQuery()).thenReturn(null)

        // When
        val result = searchRepository.getQuery()

        // Then
        Assert.assertEquals("", result)
    }

    private val _response = SearchResponse(
        weather = listOf(
            Weather(
                description = "description",
                icon = "icon"
            )
        ),
        name = "name",
        main = Main(
            temp = 0.0,
            tempMax = 0.0,
            tempMin = 0.0,
            feelsLike = 0.0,
            humidity = 1
        )
    )

    private val _searchData = SearchData(
        city = "name",
        description = "description",
        icon = "${BuildConfig.ICON_URL}icon@2x.png",
        temperature = -460,
        lowTemp = -460,
        highTemp = -460,
        feelsLike = -460,
        humidity = 1
    )
}