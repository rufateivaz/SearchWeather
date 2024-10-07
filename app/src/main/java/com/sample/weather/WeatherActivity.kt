package com.sample.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.domain.model.SearchData
import com.sample.weather.screens.NavigationItem
import com.sample.weather.screens.permission.LocationPermissionScreen
import com.sample.weather.screens.search.SearchScreen
import com.sample.weather.screens.searchdetails.SearchDetailsScreen
import com.sample.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}

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

