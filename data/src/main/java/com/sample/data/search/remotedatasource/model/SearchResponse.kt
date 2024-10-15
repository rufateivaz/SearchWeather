package com.sample.data.search.remotedatasource.model

import com.google.gson.annotations.SerializedName

/**
 * Search response that holds:
 *
 * @param weather to present weather description and icon
 * @param main to present weather details: humidity/current/lowest/highest/feels like temperature
 * @param name to present city name
 * */
data class SearchResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)

/**
 * Weather data that holds:
 *
 * @param description to present the description of the weather.
 * @param icon to present the corresponding icon description of the current weather.
 * */
data class Weather(
    val description: String,
    val icon: String
)

/**
 * Main data that holds:
 *
 * @param temp (in Kelvin) to present the current temperature.
 * @param tempMin (in Kelvin) to present the lowest temperature.
 * @param tempMax (in Kelvin) to present the highest temperature.
 * @param feelsLike (in Kelvin) to present the feels like temperature.
 * @param humidity to present the current humidity value.
 * */
data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val humidity: Int
)

