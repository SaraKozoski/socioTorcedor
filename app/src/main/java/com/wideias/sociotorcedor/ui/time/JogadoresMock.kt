package com.wideias.sociotorcedor.ui.time

import com.wideias.sociotorcedor.data.remote.ApiJogador

// ── Mock de jogadores para preview/desenvolvimento ────────
// Usado quando a API não está disponível ou para testes de UI

val jogadoresMock = listOf(

    // GOLEIROS
    ApiJogador(
        atleta_id        = 1,
        nome_popular     = "Léo Linck",
        apelido          = "Léo Linck",
        posicao          = "goalkeeper",
        camisa           = 1,
        foto             = null,
        data_nascimento  = "2001-03-15",
        local_nascimento = "Porto Alegre, BR",
        altura           = 1.90,
        peso             = 85.0,
        pe               = "direito",
        historia         = "Goleiro revelado nas categorias de base do Internacional, chegou ao Athletico em 2023 e rapidamente se tornou titular."
    ),
    ApiJogador(
        atleta_id        = 2,
        nome_popular     = "Mycael",
        apelido          = "Mycael",
        posicao          = "goalkeeper",
        camisa           = 12,
        foto             = null,
        data_nascimento  = "2003-07-22",
        local_nascimento = "Curitiba, BR",
        altura           = 1.88,
        peso             = 82.0,
        pe               = "direito",
        historia         = "Goleiro reserva formado nas categorias de base do próprio Athletico Paranaense."
    ),

    // DEFENSORES
    ApiJogador(
        atleta_id        = 3,
        nome_popular     = "Erick",
        apelido          = "Erick",
        posicao          = "defender",
        camisa           = 2,
        foto             = null,
        data_nascimento  = "1997-05-10",
        local_nascimento = "Curitiba, BR",
        altura           = 1.75,
        peso             = 72.0,
        pe               = "direito",
        historia         = "Lateral direito ágil e combativo, um dos líderes do elenco rubro-negro."
    ),
    ApiJogador(
        atleta_id        = 4,
        nome_popular     = "Kaique Rocha",
        apelido          = "Kaique",
        posicao          = "defender",
        camisa           = 4,
        foto             = null,
        data_nascimento  = "2001-11-03",
        local_nascimento = "São Paulo, BR",
        altura           = 1.92,
        peso             = 88.0,
        pe               = "esquerdo",
        historia         = "Zagueiro imponente com passagem pela Sampdoria na Itália antes de retornar ao futebol brasileiro."
    ),
    ApiJogador(
        atleta_id        = 5,
        nome_popular     = "Thiago Heleno",
        apelido          = "Thiago Heleno",
        posicao          = "defender",
        camisa           = 3,
        foto             = null,
        data_nascimento  = "1988-01-15",
        local_nascimento = "Uberaba, BR",
        altura           = 1.87,
        peso             = 84.0,
        pe               = "direito",
        historia         = "Capitão histórico do Athletico, um dos maiores zagueiros da história do clube. Bicampeão da Copa Sul-Americana."
    ),
    ApiJogador(
        atleta_id        = 6,
        nome_popular     = "Esquivel",
        apelido          = "Esquivel",
        posicao          = "defender",
        camisa           = 6,
        foto             = null,
        data_nascimento  = "1999-04-18",
        local_nascimento = "Montevidéu, UY",
        altura           = 1.78,
        peso             = 74.0,
        pe               = "esquerdo",
        historia         = "Lateral esquerdo uruguaio conhecido pela sua velocidade e qualidade nos cruzamentos."
    ),

    // MEIO-CAMPISTAS
    ApiJogador(
        atleta_id        = 7,
        nome_popular     = "Erick Pulgar",
        apelido          = "Pulgar",
        posicao          = "midfielder",
        camisa           = 8,
        foto             = null,
        data_nascimento  = "1994-01-15",
        local_nascimento = "La Serena, CL",
        altura           = 1.83,
        peso             = 78.0,
        pe               = "direito",
        historia         = "Volante chileno com passagens por Bologna e Fiorentina na Itália. Internacional pela seleção do Chile."
    ),
    ApiJogador(
        atleta_id        = 8,
        nome_popular     = "Fernandinho",
        apelido          = "Fernandinho",
        posicao          = "midfielder",
        camisa           = 5,
        foto             = null,
        data_nascimento  = "1985-05-04",
        local_nascimento = "Londrina, BR",
        altura           = 1.79,
        peso             = 67.0,
        pe               = "direito",
        historia         = "Ídolo máximo do Athletico Paranaense. Após brilhar no Shakhtar Donetsk e no Manchester City por 9 anos, retornou ao clube do coração para encerrar a carreira."
    ),
    ApiJogador(
        atleta_id        = 9,
        nome_popular     = "Christian",
        apelido          = "Christian",
        posicao          = "midfielder",
        camisa           = 11,
        foto             = null,
        data_nascimento  = "1999-02-28",
        local_nascimento = "Curitiba, BR",
        altura           = 1.74,
        peso             = 68.0,
        pe               = "direito",
        historia         = "Meia criativo revelado nas categorias de base do Athletico. Considerado uma das maiores promessas do futebol paranaense."
    ),
    ApiJogador(
        atleta_id        = 10,
        nome_popular     = "João Cruz",
        apelido          = "João Cruz",
        posicao          = "midfielder",
        camisa           = 7,
        foto             = null,
        data_nascimento  = "2003-09-12",
        local_nascimento = "Curitiba, BR",
        altura           = 1.72,
        peso             = 65.0,
        pe               = "esquerdo",
        historia         = "Jovem meio-campista promissor das categorias de base do Furacão."
    ),

    // ATACANTES
    ApiJogador(
        atleta_id        = 11,
        nome_popular     = "Pablo",
        apelido          = "Pablo",
        posicao          = "attacker",
        camisa           = 9,
        foto             = null,
        data_nascimento  = "1991-09-05",
        local_nascimento = "São Paulo, BR",
        altura           = 1.88,
        peso             = 85.0,
        pe               = "direito",
        historia         = "Centroavante artilheiro, um dos maiores ídolos da história recente do Athletico. Campeão da Copa Sul-Americana 2018."
    ),
    ApiJogador(
        atleta_id        = 12,
        nome_popular     = "Canobbio",
        apelido          = "Canobbio",
        posicao          = "attacker",
        camisa           = 17,
        foto             = null,
        data_nascimento  = "1998-10-15",
        local_nascimento = "Montevidéu, UY",
        altura           = 1.76,
        peso             = 71.0,
        pe               = "esquerdo",
        historia         = "Ponta esquerda uruguaio veloz e habilidoso. Internacional pela Celeste Olímpica, chegou ao Athletico em 2022 e rapidamente conquistou a torcida."
    ),
    ApiJogador(
        atleta_id        = 13,
        nome_popular     = "Cuello",
        apelido          = "Cuello",
        posicao          = "attacker",
        camisa           = 10,
        foto             = null,
        data_nascimento  = "2000-03-30",
        local_nascimento = "Tucumán, AR",
        altura           = 1.73,
        peso             = 69.0,
        pe               = "direito",
        historia         = "Atacante argentino habilidoso, chegou ao Furacão em 2023 vindo do San Lorenzo."
    )
)