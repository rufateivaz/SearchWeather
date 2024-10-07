package com.sample.weather.screens

enum class Screen {
    LOCATION_PERMISSION,
    SEARCH,
    SEARCH_DETAILS
}

sealed class NavigationItem(val route: String) {
    data object Search : NavigationItem(Screen.SEARCH.name)
    data object SearchDetails : NavigationItem(Screen.SEARCH_DETAILS.name)
    data object LocationPermission : NavigationItem(Screen.LOCATION_PERMISSION.name)
}