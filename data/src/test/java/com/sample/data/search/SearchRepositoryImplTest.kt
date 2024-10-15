package com.sample.data.search

import com.sample.data.BuildConfig
import com.sample.data.search.localdatasource.SearchPreferencesManager
import com.sample.data.search.remotedatasource.SearchAPIService
import com.sample.data.search.remotedatasource.model.Main
import com.sample.data.search.remotedatasource.model.SearchResponse
import com.sample.data.search.remotedatasource.model.Weather
import com.sample.domain.model.SearchData
import com.sample.domain.model.SearchDataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryImplTest {

    private lateinit var searchRepository: SearchRepositoryImpl
    private val searchPreferencesManager: SearchPreferencesManager = mock()
    private val apiService: SearchAPIService = mock()

    @Test
    fun whenGettingSearchDataStateGivenResponseIsSuccessfulThenItShouldReturnSuccessState() =
        runTest {
            // Given
            val response = Response.success(_response)
            whenever(apiService.getSearchResponse(any(), any())).thenReturn(response)
            whenever(searchPreferencesManager.setQuery(any())).then {}

            // When
            val testDispatcher = StandardTestDispatcher(testScheduler)
            searchRepository = SearchRepositoryImpl(
                apiService,
                searchPreferencesManager,
                testDispatcher
            )
            val result = searchRepository.getSearchData("")

            // Then
            Assert.assertEquals(SearchDataState.Success(_searchData), result)
            verify(searchPreferencesManager).setQuery("")
        }

    @Test
    fun whenGettingSearchDataStateGivenResponseIsNullBodyThenItShouldReturnNullErrorState() =
        runTest {
            // Given
            val searchResponse: SearchResponse? = null
            val response = Response.success(searchResponse)
            whenever(apiService.getSearchResponse(any(), any())).thenReturn(response)

            // When
            val testDispatcher = StandardTestDispatcher(testScheduler)
            searchRepository = SearchRepositoryImpl(
                apiService,
                searchPreferencesManager,
                testDispatcher
            )
            val result = searchRepository.getSearchData("")

            // Then
            val actualErrorMessage = (result as SearchDataState.Error).error.message
            val expectedErrorMessage = "Response body is null"
            Assert.assertEquals(expectedErrorMessage, actualErrorMessage)
            verify(searchPreferencesManager, never()).setQuery("")
        }

    @Test
    fun whenGettingSearchDataStateGivenUnsuccessfulResponseThenItShouldReturnErrorCode() =
        runTest {
            // Given
            val errorBody = "".toResponseBody("application/json".toMediaTypeOrNull())
            val errorResponse: Response<SearchResponse> = Response.error(404, errorBody)
            whenever(apiService.getSearchResponse(any(), any())).thenReturn(errorResponse)

            // When
            val testDispatcher = StandardTestDispatcher(testScheduler)
            searchRepository = SearchRepositoryImpl(
                apiService,
                searchPreferencesManager,
                testDispatcher
            )
            val result = searchRepository.getSearchData("")

            // Then
            val actualErrorMessage = (result as SearchDataState.Error).error.message
            val expectedErrorMessage = "Unsuccessful response: 404"
            Assert.assertEquals(expectedErrorMessage, actualErrorMessage)
            verify(searchPreferencesManager, never()).setQuery("")
        }

    @Test
    fun whenGettingSearchDataStateGivenErrorThenItShouldReturnNetworkErrorState() =
        runTest {
            // Given
            whenever(apiService.getSearchResponse(any(), any()))
                .then { throw IOException() }

            // When
            val testDispatcher = StandardTestDispatcher(testScheduler)
            searchRepository = SearchRepositoryImpl(
                apiService,
                searchPreferencesManager,
                testDispatcher
            )
            val result = searchRepository.getSearchData("")

            // Then
            val actualErrorMessage = (result as SearchDataState.Error).error.message
            val expectedErrorMessage = "Network error"
            Assert.assertEquals(expectedErrorMessage, actualErrorMessage)
            verify(searchPreferencesManager, never()).setQuery("")
        }


    @Test
    fun whenGettingQueryGivenExistItShouldGetQuery() = runTest {
        // Given
        val query = "oldQuery"
        whenever(searchPreferencesManager.getQuery()).thenReturn(query)

        // When
        val testDispatcher = StandardTestDispatcher(testScheduler)
        searchRepository = SearchRepositoryImpl(
            apiService,
            searchPreferencesManager,
            testDispatcher
        )
        val result = searchRepository.getQuery()

        // Then
        Assert.assertEquals(query, result)
    }

    @Test
    fun whenGettingQueryGivenNotExistItShouldGetEmptyString() = runTest {
        // Given
        whenever(searchPreferencesManager.getQuery()).thenReturn(null)

        // When
        val testDispatcher = StandardTestDispatcher(testScheduler)
        searchRepository = SearchRepositoryImpl(
            apiService,
            searchPreferencesManager,
            testDispatcher
        )
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