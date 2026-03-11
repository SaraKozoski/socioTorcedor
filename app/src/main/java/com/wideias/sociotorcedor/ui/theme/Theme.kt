package com.wideias.sociotorcedor.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val EsquemaEscuro = darkColorScheme(
    primary = VermelhoBotao,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = FundoEscuro,
    surface = FundoEscuro,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun SocioTorcedorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EsquemaEscuro,
        typography = Typography,
        content = content
    )
}