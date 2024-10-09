package com.sample.weather.screens.search

import android.net.Uri
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.sample.domain.model.SearchData
import com.sample.domain.model.SearchDataState
import com.sample.weather.screens.NavigationItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private val vm: SearchViewModel = mockk<SearchViewModel>(relaxed = true)
    private val navController = mockk<NavController>(relaxed = true)

    @Test
    fun verifyTextInputWithCorrectHintIsDisplayedAndSearchButtonIsDisabledButDisabled() {
        every { vm.initialSearchResultPresented } returns true
        every { vm.searchDataState.value } returns SearchDataState.Idle

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm)
        }

        composeTestRule.onNodeWithText("Search")
            .assertHasClickAction()
            .assertIsNotEnabled()

        composeTestRule.onNodeWithText("Write a city name; e.g., London")
            .assertIsDisplayed()
    }

    @Test
    fun givenSearchTextInputIsPerformedThenButtonIsEnabledAndWhenClickedGetSearchDataMethodIsCalled() {
        every { vm.initialSearchResultPresented } returns true
        every { vm.searchDataState.value } returns SearchDataState.Idle

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm)
        }

        composeTestRule.onNodeWithText("Write a city name; e.g., London")
            .assertIsDisplayed()
            .performTextInput("testCity")

        composeTestRule.onNodeWithText("Search")
            .assertHasClickAction()
            .assertIsEnabled()
            .performClick()

        verify {
            vm.getSearchData("testCity")
        }
    }

    @Test
    fun givenQuerySearchResultIsSuccessfulThenNavigateToSearchDetailsScreen() {
        every { vm.initialSearchResultPresented } returns true
        every { vm.searchDataState.value } returns SearchDataState.Success(_searchData)

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm)
        }

        val searchDataJson = Uri.encode(Json.encodeToString(_searchData))
        val route = "${NavigationItem.SearchDetails.route}/$searchDataJson"
        verify {
            navController.navigate(route)
            vm.initSearchDataState()
        }
    }

    @Test
    fun givenInitialSearchResultNotPresentedAndCityParamIsEmptyThenGetSearchDataWithOldQueryIsCalled() {
        every { vm.initialSearchResultPresented } returns false
        every { vm.searchDataState.value } returns SearchDataState.Idle

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm)
        }

        verify {
            vm.getSearchDataWithOldQuery()
        }
    }

    @Test
    fun givenInitialSearchResultNotPresentedAndCityParamIsNotEmptyThenGetSearchDataIsCalled() {
        every { vm.initialSearchResultPresented } returns false
        every { vm.searchDataState.value } returns SearchDataState.Idle

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm, city = "testCity")
        }

        verify {
            vm.getSearchData("testCity")
        }
    }

    @Test
    fun givenSearchDataStateIsErrorThenTheErrorMessageShouldBeDisplayed() {
        every { vm.initialSearchResultPresented } returns true
        every { vm.searchDataState.value } returns SearchDataState.Error(Exception())

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm)
        }

        composeTestRule.onNodeWithText("The query is not successful.")
    }

    @Test
    fun givenSearchDataStateIsLoadingThenTheLoadingIndicatorShouldBeDisplayed() {
        every { vm.initialSearchResultPresented } returns true
        every { vm.searchDataState.value } returns SearchDataState.Loading

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = vm)
        }

        composeTestRule.onNodeWithTag("progressIndicator")
    }

    private val _searchData = SearchData(
        city = "City",
        temperature = 0,
        lowTemp = -1,
        highTemp = 1,
        feelsLike = 1,
        icon = "icon",
        description = "Description",
        humidity = 0
    )
}