package com.sample.domain.model

import kotlinx.serialization.Serializable

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