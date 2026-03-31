package com.wideias.sociotorcedor.ui.time

// ── Modelo de jogo ────────────────────────────────────────
data class Jogo(
    val competicao: String,
    val data: String,
    val timeCasa: String,
    val timeVisitante: String,
    val placarCasa: Int,
    val placarVisitante: Int,
    val aoVivo: Boolean = false,
    val futuro: Boolean = false
)

// ── Mock de jogos do Athletico ────────────────────────────
val jogosMock = listOf(

    // Jogo ao vivo
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "hoje",
        timeCasa         = "CAP",
        timeVisitante    = "FLA",
        placarCasa       = 1,
        placarVisitante  = 0,
        aoVivo           = true
    ),

    // Próximo jogo (futuro)
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "22/06/2025",
        timeCasa         = "CAP",
        timeVisitante    = "PAL",
        placarCasa       = 0,
        placarVisitante  = 0,
        futuro           = true
    ),

    // Jogos passados
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "15/06/2025",
        timeCasa         = "CAP",
        timeVisitante    = "COR",
        placarCasa       = 3,
        placarVisitante  = 1
    ),
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "08/06/2025",
        timeCasa         = "FLU",
        timeVisitante    = "CAP",
        placarCasa       = 0,
        placarVisitante  = 2
    ),
    Jogo(
        competicao       = "Copa do Brasil",
        data             = "04/06/2025",
        timeCasa         = "CAP",
        timeVisitante    = "GRE",
        placarCasa       = 2,
        placarVisitante  = 2
    ),
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "01/06/2025",
        timeCasa         = "CAP",
        timeVisitante    = "BOT",
        placarCasa       = 1,
        placarVisitante  = 1
    ),
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "25/05/2025",
        timeCasa         = "SAO",
        timeVisitante    = "CAP",
        placarCasa       = 0,
        placarVisitante  = 1
    ),
    Jogo(
        competicao       = "Copa Sul-Americana",
        data             = "21/05/2025",
        timeCasa         = "CAP",
        timeVisitante    = "LDU",
        placarCasa       = 2,
        placarVisitante  = 0
    ),
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "18/05/2025",
        timeCasa         = "CAP",
        timeVisitante    = "CRU",
        placarCasa       = 3,
        placarVisitante  = 2
    ),
    Jogo(
        competicao       = "Serie A - Brasileirão",
        data             = "11/05/2025",
        timeCasa         = "INT",
        timeVisitante    = "CAP",
        placarCasa       = 1,
        placarVisitante  = 2
    )
)