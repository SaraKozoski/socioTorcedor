package com.wideias.sociotorcedor.ui.home

import androidx.compose.ui.graphics.Color

data class Partida(
    val timeCasa: String,
    val timeVisitante: String,
    val placarCasa: Int = 0,
    val placarVisitante: Int = 0,
    val data: String,
    val competicao: String = "Serie A - Brasileirão",
    val aoVivo: Boolean = false,
    val futura: Boolean = false,
    val gols: List<String> = emptyList()
)

data class Plano(
    val nome: String,
    val preco: String,
    val cor: Color,
    val beneficios: List<String>
)

val partidasMock = listOf(
    // Próximo jogo (futura)
    Partida(
        timeCasa = "CAP",
        timeVisitante = "CFC",
        data = "20/03/2026",
        competicao = "Serie A - Brasileirão",
        futura = true
    ),
    // Jogo ao vivo ou mais recente (principal)
    Partida(
        timeCasa = "CAP",
        timeVisitante = "CFC",
        placarCasa = 0,
        placarVisitante = 0,
        data = "Hoje",
        competicao = "Serie A - Brasileirão",
        aoVivo = true
    ),
    // Últimos jogos (secundários)
    Partida(
        timeCasa = "CAP",
        timeVisitante = "PAR",
        placarCasa = 2,
        placarVisitante = 2,
        data = "30/01/2025",
        gols = listOf("4° - J. Jonas", "25° - J. Jonas")
    ),
    Partida(
        timeCasa = "CAP",
        timeVisitante = "GRE",
        placarCasa = 1,
        placarVisitante = 0,
        data = "25/01/2025",
        gols = listOf("10° - R. Ramos")
    ),
    Partida(
        timeCasa = "CAP",
        timeVisitante = "INT",
        placarCasa = 3,
        placarVisitante = 1,
        data = "20/01/2025",
        gols = listOf("5° - J. Jonas", "44° - Di Yorio", "78° - Mastriani")
    ),
    Partida(
        timeCasa = "CAP",
        timeVisitante = "FLA",
        placarCasa = 0,
        placarVisitante = 1,
        data = "15/01/2025",
        gols = emptyList()
    )
)

val planosMock = listOf(
    Plano(
        nome = "RED",
        preco = "R$ 119,90/ mês",
        cor = HomeColors.FundoCards1,
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
        cor = HomeColors.Cards2,
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
        cor = HomeColors.FundoCard3,
        beneficios = listOf(
            "Direito a voto nas eleições do clube",
            "Clube de vantagens",
            "Descontos na loja oficial",
            "Participação em ações exclusivas",
            "Cadeira com nome em todos os jogos"
        )
    )
)