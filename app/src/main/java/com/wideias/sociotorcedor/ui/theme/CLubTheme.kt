package com.wideias.sociotorcedor.ui.theme

import androidx.compose.ui.graphics.Color
import com.wideias.sociotorcedor.R

object ClubConfig {

    const val NOME_CLUBE        = "Soccer Club"
    const val SIGLA_CLUBE       = "CAP"
    const val TIME_ID_API       = 134          // ID do time na API de partidas

    val logoRes         = R.drawable.logo_clube
    val logoBrancoRes   = R.drawable.logo_branco

    //   primaryMain   → cor principal do clube (botões, destaques, chips ativos)
    //   primaryDark   → variação escura (fundo de cards de destaque, gradientes)
    //   primaryFaint  → variação translúcida (fundos sutis, chips inativos)
    val primaryMain  = Color(0xFFBA0000)   // vermelho vibrante
    val primaryDark  = Color(0xFF8B0000)   // vermelho escuro / Dark Red
    val primaryFaint = Color(0x80BA0000)   // vermelho 50% opaco

    val tierRed   = primaryFaint           // Plano RED  → cor primária translúcida
    val tierGold  = Color(0x33FFFFD700)    // Plano GOLD → dourado translúcido
    val tierBlack = Color(0xFF101010)      // Plano BLACK → quase-preto

    val background      = Color(0xFF282828)  // fundo geral do app
    val surfaceDark     = Color(0xFF1E1E1E)  // cards escuros, topbars
    val surfaceLight    = Color(0xFFF5F5F5)  // cards claros (ex: BetCard header)
    val surfaceSubtle   = Color(0xFF0A0A0A)  // preto suave (texto sobre claro)

    val onSurface       = Color.White
    val onSurfaceMuted  = Color(0xFFAAAAAA)  // texto secundário / cinza
    val onLight         = Color(0xFF0A0A0A)  // texto sobre fundos claros

    val loginFieldBg    = primaryDark        // fundo dos campos no login
    val loginButton     = primaryMain        // botão primário no login

    val estadioDefaultRes = R.drawable.estadio_default
    val estadioRedRes     = R.drawable.estadio_red
    val estadioGoldRes    = R.drawable.estadio_gold
    val estadioBlackRes   = R.drawable.estadio_black
    val estadioMapaRes    = R.drawable.estadio_mapa
}


object AppColors {

    // Superfícies
    val background    get() = ClubConfig.background
    val cardDark      get() = ClubConfig.surfaceDark
    val cardLight     get() = ClubConfig.surfaceLight
    val cardAccent    get() = ClubConfig.primaryDark   // ex: imageBox de produto

    // Marca / interação
    val brand         get() = ClubConfig.primaryMain
    val brandDark     get() = ClubConfig.primaryDark
    val brandFaint    get() = ClubConfig.primaryFaint

    // Texto
    val textPrimary   get() = ClubConfig.onSurface
    val textSecondary get() = ClubConfig.onSurfaceMuted
    val textOnLight   get() = ClubConfig.onLight

    // Chips / filtros
    val chipActive    get() = ClubConfig.onSurface
    val chipInactive  get() = Color(0xFF6B0000)

    // Bottombar
    val bottomBar     get() = Color(0xFF121212)

    // Tiers de planos
    val tierRed       get() = ClubConfig.tierRed
    val tierGold      get() = ClubConfig.tierGold
    val tierBlack     get() = ClubConfig.tierBlack
}