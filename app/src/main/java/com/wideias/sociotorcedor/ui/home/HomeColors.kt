package com.wideias.sociotorcedor.ui.home

import androidx.compose.ui.graphics.Color
import com.wideias.sociotorcedor.ui.theme.AppColors

// ─────────────────────────────────────────────────────────────────────────────
//  HomeColors — mantido para compatibilidade com imports existentes.
//  Todos os valores agora vêm de AppColors, que por sua vez lê ClubConfig.
//  Não adicione cores aqui diretamente; edite ClubConfig / AppColors.
// ─────────────────────────────────────────────────────────────────────────────

object HomeColors {
    val Fundo           get() = AppColors.background
    val CardEscuro      get() = AppColors.cardDark
    val CardClaro       get() = AppColors.cardLight
    val Cards1          get() = AppColors.brand
    val DetalhesCard1   get() = AppColors.brandDark
    val FundoCards1     get() = AppColors.brandFaint
    val Cards2          get() = AppColors.tierGold
    val FundoCard3      get() = Color(0x800A0A0A)
    val Card3ComTexto   get() = AppColors.tierBlack
    val Preto           get() = AppColors.textOnLight
    val TextoBranco     get() = AppColors.textPrimary
    val TextoCinza      get() = AppColors.textSecondary
    val ChipSelecionado get() = AppColors.chipActive
    val ChipNormal      get() = AppColors.chipInactive
    val BottomBar       get() = AppColors.bottomBar
}