package com.sample.weather.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Light Mode Blue Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),           // Deep Blue
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBBDEFB),  // Lighter Blue
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF03A9F4),         // Light Blue
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFB3E5FC),
    onSecondaryContainer = Color(0xFF01579B),
    background = Color(0xFFE3F2FD),        // Light Blue Background
    onBackground = Color(0xFF0D47A1),
    surface = Color(0xFFFAFAFA),
    onSurface = Color(0xFF0D47A1),
    error = Color(0xFFD32F2F),
    onError = Color.White,
    outline = Color(0xFF90CAF9),
    surfaceVariant = Color(0xFFBBDEFB),
    onSurfaceVariant = Color(0xFF1976D2),
    inverseOnSurface = Color(0xFFF5F5F5),
    inverseSurface = Color(0xFF1A237E),
)

// Dark Mode Blue Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF82B1FF),           // Light Blue
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1976D2),  // Darker Blue
    onPrimaryContainer = Color(0xFFBBDEFB),
    secondary = Color(0xFF03A9F4),         // Light Blue
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF0288D1),
    onSecondaryContainer = Color(0xFFB3E5FC),
    background = Color(0xFF0D47A1),        // Deep Blue Background
    onBackground = Color(0xFFBBDEFB),
    surface = Color(0xFF1C1C1C),
    onSurface = Color(0xFFE0E0E0),
    error = Color(0xFFCF6679),
    onError = Color.Black,
    outline = Color(0xFF90CAF9),
    surfaceVariant = Color(0xFF1976D2),
    onSurfaceVariant = Color(0xFFBBDEFB),
    inverseOnSurface = Color(0xFF1C1C1C),
    inverseSurface = Color(0xFFE0E0E0),
)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}