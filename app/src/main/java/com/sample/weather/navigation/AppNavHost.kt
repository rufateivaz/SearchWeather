package com.sample.weather.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.domain.search.model.SearchData
import com.sample.weather.feature.locationpermission.LocationPermissionScreen
import com.sample.weather.feature.search.SearchScreen
import com.sample.weather.feature.searchdetails.SearchDetailsScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.LocationPermission.route,
) {
    NavHost(
        modifier = modifier.background(color = Color.White),
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.LocationPermission.route) {
            LocationPermissionScreen(navController)
        }
        composable("${NavigationItem.Search.route}/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city")
            city?.let { SearchScreen(navController, city) }
        }
        composable("${NavigationItem.SearchDetails.route}/{searchData}") { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("searchData")
            userJson?.let {
                val searchData = Json.decodeFromString<SearchData>(it)
                SearchDetailsScreen(navController, searchData)
            }
        }
    }
}