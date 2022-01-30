package com.sunnyoaklabs.manodienynas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.sunnyoaklabs.manodienynas.ui.custom.LocalSpacing
import com.sunnyoaklabs.manodienynas.ui.custom.Spacing

private val DarkColorPalette = darkColors(
    primary = primaryGreenAccent,
    primaryVariant = primaryVariantGreenLight,
    secondary = secondaryGreenDark
)

private val LightColorPalette = lightColors(
    primary = primaryGreenAccent,
    primaryVariant = primaryVariantGreenLight,
    secondary = secondaryGreenDark

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ManoDienynasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    
    CompositionLocalProvider(
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}