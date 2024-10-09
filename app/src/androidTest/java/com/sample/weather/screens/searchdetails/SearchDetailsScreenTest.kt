package com.sample.weather.screens.searchdetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.sample.domain.model.SearchData
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchDetailsScreenTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private val navController = mockk<NavController>(relaxed = true)

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SearchDetailsScreen(
                navController = navController,
                searchData = SearchData(
                    city = "City",
                    temperature = 0,
                    lowTemp = -1,
                    highTemp = 1,
                    feelsLike = 1,
                    icon = "icon",
                    description = "Description",
                    humidity = 0
                )
            )
        }
    }

    @Test
    fun verifyTextsAreDisplayedAndBackButton() {
        val detailedDesc = "In City, " +
                "the temperature is 0°, " +
                "and feels like 1°." +
                " Today, it is ranged from -1° to 1°." +
                " Currently, Description can be observed," +
                " and the humidity is 0."

        composeTestRule.onNodeWithText("City").assertIsDisplayed()
        composeTestRule.onNodeWithText("0°F").assertIsDisplayed()
        composeTestRule.onNodeWithText("L:-1° H:1°").assertIsDisplayed()
        composeTestRule.onNodeWithText(detailedDesc).assertIsDisplayed()
    }

    @Test
    fun verifyThatBackButtonIsDisplayedAndWhenPerformedClickItGoesBack() {
        composeTestRule.onNodeWithTag("backButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("backButton").performClick()

        verify {
            navController.popBackStack()
        }
    }
}