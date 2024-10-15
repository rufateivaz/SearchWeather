package com.sample.data.search.extensions

import com.sample.data.BuildConfig
import com.sample.data.search.remotedatasource.model.SearchResponse
import com.sample.data.search.utils.toFahrenheit
import com.sample.domain.search.model.SearchData

/**
 * Converts search response to domain model: SearchData
 * */
fun SearchResponse.toDomainModel(): SearchData = SearchData(
    city = name,
    temperature = toFahrenheit(main.temp),
    lowTemp = toFahrenheit(main.tempMin),
    highTemp = toFahrenheit(main.tempMax),
    feelsLike = toFahrenheit(main.feelsLike),
    humidity = main.humidity,
    description = if (weather.isNotEmpty()) weather[0].description else "",
    icon = if (weather.isNotEmpty()) "${BuildConfig.ICON_URL}${weather[0].icon}@2x.png" else ""
)