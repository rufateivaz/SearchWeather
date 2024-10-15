package com.sample.weather.feature.locationpermission

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavController
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LocationPermissionScreenKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private val navController = mockk<NavController>(relaxed = true)

    @Test
    fun whenLocationPermissionScreenIsLaunchedPermissionRelatedOptionsShouldBePresented() {
        composeTestRule.setContent {
            LocationPermissionScreen(navController = navController)
        }

        composeTestRule.onNodeWithText("While using the app")
        composeTestRule.onNodeWithText("Only this time")
        composeTestRule.onNodeWithText("Don't allow")
    }
}