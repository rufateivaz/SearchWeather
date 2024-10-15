package com.sample.data.utils

import com.sample.data.search.utils.toFahrenheit
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun whenConvertingFromKelvinToFahrenheitItShouldConvert() {
        // Given
        val kelvin = 300.5
        val expected = 81

        // When
        val actual = toFahrenheit(kelvin)

        // Then
        Assert.assertEquals(actual, expected)
    }
}