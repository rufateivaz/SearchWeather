package com.sample.domain.search.model

import kotlinx.serialization.Serializable

/**
 * SearchData that holds the following data.
 *
 * @param city the city name.
 * @param temperature the current temperature
 * @param lowTemp the lowest temperature for the day.
 * @param highTemp the highest temperature for the day.
 * @param feelsLike the temperature that feels like due to wind, humidity, etc.
 * @param humidity the humidity value.
 * */
@Serializable
data class SearchData(
    val city: String,
    val temperature: Int,
    val lowTemp: Int,
    val highTemp: Int,
    val feelsLike: Int,
    val icon: String,
    val description: String,
    val humidity: Int
)