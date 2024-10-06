package com.sample.data.utils

import kotlin.math.roundToInt

fun toFahrenheit(kelvin: Double): Int = ((kelvin - 273.15) * 1.8 + 32).roundToInt()
