package com.jetpack.kawanusaha.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = TextDayNBackgroundNight,
    onPrimary = TextNightNBackgroundDay,
    primaryVariant = PrimaryVariantNight,
    secondary = SecondaryNight,
    secondaryVariant = TextNightNBackgroundDay,
    background = PrimaryNight,
    surface = Grey
)

private val LightColorPalette = lightColors(
    primary = TextNightNBackgroundDay,
    onPrimary = TextDayNBackgroundNight,
    primaryVariant = PrimaryVariantDay,
    secondary = SecondaryDay,
    secondaryVariant = PrimaryVariantNight,
    background = PrimaryDay,
    surface = Grey

    /* Other default colors to override
    background = Color.TextNightNBackgroundDay,
    surface = Color.TextNightNBackgroundDay,
    onPrimary = Color.TextNightNBackgroundDay,
    onSecondary = Color.TextDayNBackgroundNight,
    onPrimary = Color.TextDayNBackgroundNight,
    onSurface = Color.TextDayNBackgroundNight,
    */
)

@Composable
fun KawanUsahaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}