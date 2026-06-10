private val jogosMock = listOf(
    JogoDisponivel(
        id           = 1,
        mandante     = "Atlético",
        visitante    = "Flamengo",
        competicao   = "Brasileirão · Rodada 14",
        data         = "07 Jun, Sáb",
        hora         = "16h00",
        estadio      = "Arena MRV",
        precoMinimo  = 45.0,
        precoOriginal = 50.0,
        status       = StatusIngresso.DISPONIVEL
    ),
    JogoDisponivel(
        id           = 2,
        mandante     = "Atlético",
        visitante    = "Corinthians",
        competicao   = "Copa do Brasil · Oitavas",
        data         = "12 Jun, Qui",
        hora         = "19h30",
        estadio      = "Arena MRV",
        precoMinimo  = 60.0,
        precoOriginal = 80.0,
        status       = StatusIngresso.DISPONIVEL
    ),
    JogoDisponivel(
        id           = 3,
        mandante     = "Atlético",
        visitante    = "Palmeiras",
        competicao   = "Brasileirão · Rodada 15",
        data         = "15 Jun, Dom",
        hora         = "18h00",
        estadio      = "Arena MRV",
        precoMinimo  = null,
        status       = StatusIngresso.ESGOTADO
    ),
    JogoDisponivel(
        id           = 4,
        mandante     = "Atlético",
        visitante    = "Nacional",
        competicao   = "Libertadores · Fase de Grupos",
        data         = "22 Jun, Dom",
        hora         = "21h30",
        estadio      = "Arena MRV",
        precoMinimo  = null,
        status       = StatusIngresso.EM_BREVE,
        dataAbertura = "18 Jun"
    ),
    JogoDisponivel(
        id           = 5,
        mandante     = "Atlético",
        visitante    = "São Paulo",
        competicao   = "Brasileirão · Rodada 16",
        data         = "28 Jun, Sáb",
        hora         = "17h00",
        estadio      = "Arena MRV",
        precoMinimo  = null,
        status       = StatusIngresso.EM_BREVE,
        dataAbertura = "23 Jun"
    )
)

