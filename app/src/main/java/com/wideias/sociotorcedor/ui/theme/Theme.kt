package com.wideias.sociotorcedor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = darkColorScheme(
    primary    = ClubConfig.primaryMain,
    secondary  = PurpleGrey80,
    tertiary   = Pink80,
    background = ClubConfig.background,
    surface    = ClubConfig.background,
    onBackground = Color.White,
    onSurface    = Color.White
)

@Composable
fun SocioTorcedorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography  = Typography,
        content     = content
    )
}