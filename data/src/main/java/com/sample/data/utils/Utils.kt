package com.sample.data.utils

import kotlin.math.roundToInt

/**
 * Converts kelvin to fahrenheit and rounds to integer value.
 * */
fun toFahrenheit(kelvin: Double): Int = ((kelvin - 273.15) * 1.8 + 32).roundToInt()
