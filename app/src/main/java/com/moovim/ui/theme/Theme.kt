package com.moovim.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
        primary = Red500,
        secondary = Gray200,
        secondaryVariant = Gray100,
        background = Black500,
        error = Yellow500,
        onPrimary = Color.White,
        onBackground = Color.White
)

@Composable
fun MoovimTheme(content: @Composable () -> Unit) {

    MaterialTheme(
            colors = DarkColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}

object NoRippleTheme : RippleTheme {
        @Composable
        override fun defaultColor(): Color = Color.Transparent

        @Composable
        override fun rippleAlpha() = RippleAlpha(
                draggedAlpha = 0.0f,
                focusedAlpha = 0.0f,
                hoveredAlpha = 0.0f,
                pressedAlpha = 0.0f,
        )
}