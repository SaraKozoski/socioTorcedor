package com.wideias.sociotorcedor.ui.theme

import androidx.compose.ui.graphics.Color

// ── Cores do Material (mantidas para o MaterialTheme) ────────────────────────
val Purple80      = Color(0xFFD0BCFF)
val PurpleGrey80  = Color(0xFFCCC2DC)
val Pink80        = Color(0xFFEFB8C8)
val Purple40      = Color(0xFF6650a4)
val PurpleGrey40  = Color(0xFF625b71)
val Pink40        = Color(0xFF7D5260)

// ── Atalhos de conveniência para o login (derivados de ClubConfig) ────────────
//   Use ClubConfig.loginFieldBg / ClubConfig.loginButton diretamente nos
//   composables, ou mantenha estas vals para não quebrar imports legados.
val VermelhoFundoLogin get() = ClubConfig.loginFieldBg
val VermelhoBotao      get() = ClubConfig.loginButton

// ── Fundo e texto globais ────────────────────────────────────────────────────
val FundoEscuro get() = ClubConfig.background
val TextoPreto  = Color(0xFF000000)
val White       = Color(0xFFFFFFFF)