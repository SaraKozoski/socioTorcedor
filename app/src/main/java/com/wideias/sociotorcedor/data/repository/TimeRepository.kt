package com.wideias.sociotorcedor.data.repository

import com.wideias.sociotorcedor.data.remote.*
import com.wideias.sociotorcedor.ui.time.Jogo
import com.wideias.sociotorcedor.ui.time.jogosMock
import android.util.Log

class TimeRepository {

    private val api = ApiFootballService.create()

    private val API_KEY   = "d8711c94e050e1fe32d7d06f94dadc6f"
    val TIME_ID           = 134
    val LEAGUE_ID         = 71
    val SEASON            = 2024

    suspend fun getPartidas(): Result<List<ApiPartida>> {
        val partidas = jogosMock.mapIndexed { index, jogo ->
            jogo.toApiPartida(id = index + 1)
        }
        return Result.success(partidas)
    }

    suspend fun getElenco(): Result<List<ApiJogador>> {
        return try {
            val response = api.getElenco(API_KEY, TIME_ID)

            val timeCorreto = response.response.find { it.team.id == TIME_ID }

            val jogadores = timeCorreto
                ?.players
                ?.map { it.toApiJogador() }
                ?: response.response.firstOrNull()?.players?.map { it.toApiJogador() }
                ?: emptyList()

            Log.d("TimeRepository", "Elenco: ${jogadores.size}")
            Result.success(jogadores)

        } catch (e: Exception) {
            Log.e("TimeRepository", "Erro elenco", e)
            Result.failure(e)
        }
    }

        suspend fun getClassificacao(): Result<List<ApiClassificacao>> {
        return try {
            val response = api.getClassificacao(API_KEY, LEAGUE_ID, SEASON)

            val classificacao = response.response
                .firstOrNull()
                ?.league
                ?.standings
                ?.firstOrNull()
                ?.map { it.toApiClassificacao() }
                ?: emptyList()

            Result.success(classificacao)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun Jogo.toApiPartida(id: Int): ApiPartida {
    val status = when {
        aoVivo -> "ao_vivo"
        futuro -> "agendado"
        else   -> "encerrado"
    }

    return ApiPartida(
        partida_id       = id,
        data_realizacao  = data,
        hora_realizacao  = "",
        placar           = if (!futuro && !aoVivo) "$placarCasa x $placarVisitante" else null,
        status           = status,
        time_mandante    = ApiTime(
            time_id      = if (timeCasa == "CAP") 134 else 0,
            nome_popular = timeCasa,
            sigla        = timeCasa,
            escudo       = ""
        ),
        time_visitante   = ApiTime(
            time_id      = if (timeVisitante == "CAP") 134 else 0,
            nome_popular = timeVisitante,
            sigla        = timeVisitante,
            escudo       = ""
        ),
        placar_mandante  = if (!futuro) placarCasa else null,
        placar_visitante = if (!futuro) placarVisitante else null,
        campeonato       = ApiCampeonato(
            campeonato_id = 0,
            nome          = competicao,
            slug          = competicao.lowercase().replace(" ", "-")
        )
    )
}


private fun RawSquadPlayer.toApiJogador(): ApiJogador {
    return ApiJogador(
        atleta_id        = id,
        nome_popular     = name,
        apelido          = name,
        posicao          = position ?: "unknown",
        camisa           = number,
        foto             = photo,
        data_nascimento  = "20/10/2000",
        local_nascimento = "Ubatuba, SP",
        altura           = 1.80,
        peso             = null,
        pe               = "direito",
        historia         = " ${name ?: "desconecido"} cresceu nas ruas de Ubatuba, onde a bola nunca desgrudava do pé e o sonho de ser jogador parecia grande demais para quem jogava descalço na calçada. Com talento, disciplina e muita vontade, ele chamou a atenção do Soccer Club, vestiu a camisa com orgulho e transformou cada treino em uma chance de provar seu valor. Hoje, João Jonas entra em campo carregando a força da sua cidade, a paixão da torcida e a certeza de que ainda está só no começo de uma história feita de gols, superação e amor pelo futebol."
        )
}

private fun RawStandingItem.toApiClassificacao(): ApiClassificacao {
    return ApiClassificacao(
        posicao     = rank,
        time        = ApiTime(
            time_id      = team.id,
            nome_popular = team.name,
            sigla        = team.name.take(3).uppercase(),
            escudo       = team.logo
        ),
        pontos      = points,
        jogos       = all.played,
        vitorias    = all.win,
        empates     = all.draw,
        derrotas    = all.lose,
        gols_pro    = all.goals.`for`,
        gols_contra = all.goals.against,
        saldo_gols  = goalsDiff
    )
}