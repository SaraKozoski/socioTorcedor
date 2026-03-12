package com.wideias.sociotorcedor.ui.home

import androidx.compose.ui.graphics.Color

data class Partida(
    val timeCasa: String,
    val timeVisitante: String,
    val placarCasa: Int,
    val placarVisitante: Int,
    val data: String,
    val aoVivo: Boolean = false
)

data class Plano(
    val nome: String,
    val preco: String,
    val cor: Color,
    val beneficios: List<String>
)

val partidasMock = listOf(
    Partida("CRU", "FLA", 0, 0, "Hoje", aoVivo = true),
    Partida("PAL", "SAO", 2, 1, "15/03"),
    Partida("GRE", "INT", 1, 1, "14/03"),
    Partida("ATL", "BOT", 3, 0, "13/03")
)

val planosMock = listOf(
    Plano(
        nome = "RED",
        preco = "R$ 119,90/ mês",
        cor = HomeColors.VermelhoTransparente,
        beneficios = listOf(
            "Direito a voto nas eleições do clube",
            "Clube de vantagens",
            "Descontos na loja oficial",
            "Participação em ações exclusivas"
        )
    ),
    Plano(
        nome = "GOLD",
        preco = "R$ 229,90/ mês",
        cor = HomeColors.Dourado.copy(alpha = 0.5f),
        beneficios = listOf(
            "Direito a voto nas eleições do clube",
            "Clube de vantagens",
            "Descontos na loja oficial",
            "Participação em ações exclusivas",
            "Cadeira com nome em todos os jogos"
        )
    ),
    Plano(
        nome = "BLACK",
        preco = "R$ 339,90/ mês",
        cor = HomeColors.PretoTransparente,
        beneficios = listOf(
            "Direito a voto nas eleições do clube",
            "Clube de vantagens",
            "Descontos na loja oficial",
            "Participação em ações exclusivas",
            "Cadeira com nome em todos os jogos"
        )
    )
)