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

    // Apostas
    val apostasFundo         = Color(0xFF0D0D0D)
    val apostasGradienteTopo = Color(0xFF1A0A00)
    val apostasSuperficieA   = Color(0xFF161616)
    val apostasSuperficieB   = Color(0xFF1F1F1F)
    val apostasBorda         = Color(0xFF2C2C2C)
    val apostasDourado       = Color(0xFFFFBF00)
    val apostasDouradoClaro  = Color(0xFFFFF0A0)
    val apostasTextoPrimario = Color(0xFFF2F2F2)
    val apostasTextoSecund   = Color(0xFF888888)
    val apostasVerde         = Color(0xFF00C97A)

    // Home - cards de resultado
    val homeCardSecundario = Color(0xCC482B2B)
    val homeCardPrincipal  = Color(0xFF2A0A0A)

    // Ingressos / Meu Espaço
    val cardEscuro2   = Color(0xFF1E1E1E)
    val textoSecund2  = Color(0xFFAAAAAA)
    val amareloFundo  = Color(0xFFFFC107)
    val verdeConfirm  = Color(0xFF4CAF50)
    val bordaCard     = Color(0xFF2A2A2A)

    // Planos / mapa do estádio
    val zonaGold  = Color(0xFFFFD700)
    val zonaBlack = Color(0xFF111111)

    // Time (elenco, classificação, partidas)
    val timeFundoTabela   = Color(0xFF2A1515)
    val timeFundoHeader   = Color(0xFF3D1E1E)
    val timeFundoLinha    = Color(0xFF221010)
    val timeFundoLinhaAlt = Color(0xFF1C0D0D)
    val timeDestaqueLinha = Color(0xFF7C1010)
    val timeCardBorda     = Color(0xFF5A2A2A)
    val timeDivisorCor    = Color(0xFF3D2020)
    val timeAoVivoBorda   = Color(0xFFA01414)
    val timeBotaoFundo    = Color(0xFF9E1A1A)
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
    
    // Apostas
    val apostasFundo         get() = ClubConfig.apostasFundo
    val apostasGradienteTopo get() = ClubConfig.apostasGradienteTopo
    val apostasSurfaceA      get() = ClubConfig.apostasSuperficieA
    val apostasSurfaceB      get() = ClubConfig.apostasSuperficieB
    val apostasBorder        get() = ClubConfig.apostasBorda
    val apostasGold          get() = ClubConfig.apostasDourado
    val apostasGoldLight     get() = ClubConfig.apostasDouradoClaro
    val apostasTextPrimary   get() = ClubConfig.apostasTextoPrimario
    val apostasTextSecondary get() = ClubConfig.apostasTextoSecund
    val apostasGreen         get() = ClubConfig.apostasVerde

    // Home cards
    val homeCardSecundario get() = ClubConfig.homeCardSecundario
    val homeCardPrincipal  get() = ClubConfig.homeCardPrincipal

    // Ingressos / Meu Espaço
    val cardDark2      get() = ClubConfig.cardEscuro2
    val textSecondary2 get() = ClubConfig.textoSecund2
    val warning        get() = ClubConfig.amareloFundo
    val success        get() = ClubConfig.verdeConfirm
    val cardBorder     get() = ClubConfig.bordaCard

    // Planos
    val zonaRed   get() = ClubConfig.primaryMain
    val zonaGold  get() = ClubConfig.zonaGold
    val zonaBlack get() = ClubConfig.zonaBlack

    // Time
    val timeTableBg      get() = ClubConfig.timeFundoTabela
    val timeHeaderBg     get() = ClubConfig.timeFundoHeader
    val timeRowBg        get() = ClubConfig.timeFundoLinha
    val timeRowAltBg     get() = ClubConfig.timeFundoLinhaAlt
    val timeHighlightRow get() = ClubConfig.timeDestaqueLinha
    val timeCardBorder   get() = ClubConfig.timeCardBorda
    val timeDivider      get() = ClubConfig.timeDivisorCor
    val timeLiveBorder   get() = ClubConfig.timeAoVivoBorda
    val timeButtonBg     get() = ClubConfig.timeBotaoFundo

    }