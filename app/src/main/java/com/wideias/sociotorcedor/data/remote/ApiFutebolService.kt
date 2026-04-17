package com.wideias.sociotorcedor.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class RawTeam(
    val id: Int,
    val name: String,
    val code: String?,
    val logo: String
)

data class RawTeamWrapper(val team: RawTeam)

data class RawGoals(val home: Int?, val away: Int?)

data class RawScore(val fulltime: RawGoals)

data class RawFixtureStatus(
    val short: String  
)

data class RawFixture(
    val id: Int,
    val date: String,
    val status: RawFixtureStatus
)

data class RawLeague(
    val id: Int,
    val name: String,
    val season: Int
)

data class RawFixtureItem(
    val fixture: RawFixture,
    val league: RawLeague,
    val teams: RawTeamsFixture,
    val goals: RawGoals,
    val score: RawScore
)

data class RawTeamsFixture(
    val home: RawTeamFixture,
    val away: RawTeamFixture
)

data class RawTeamFixture(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean?
)

data class RawFixtureResponse(val response: List<RawFixtureItem>)

// Elenco
data class RawPlayer(
    val id: Int,
    val name: String,
    val firstname: String,
    val lastname: String,
    val age: Int?,
    val birth: RawBirth?,
    val nationality: String?,
    val height: String?,
    val weight: String?,
    val photo: String?
)

data class RawBirth(val date: String?, val place: String?, val country: String?)

data class RawPlayerStatistics(
    val games: RawGames?,
    val goals: RawPlayerGoals?
)

data class RawGames(val position: String?, val number: Int?)

data class RawPlayerGoals(val total: Int?)

data class RawSquadItem(
    val player: RawPlayer,
    val statistics: List<RawPlayerStatistics>
)

data class RawSquadResponse(val response: List<RawSquadTeamItem>)

data class RawStandingTeam(
    val id: Int,
    val name: String,
    val logo: String
)

data class RawStandingAll(
    val played: Int,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val goals: RawStandingGoals
)

data class RawStandingGoals(val `for`: Int, val against: Int)

data class RawStandingItem(
    val rank: Int,
    val team: RawStandingTeam,
    val points: Int,
    val goalsDiff: Int,
    val all: RawStandingAll
)

data class RawLeagueStandings(val standings: List<List<RawStandingItem>>)

data class RawStandingLeagueWrapper(val league: RawLeagueStandings)

data class RawStandingsResponse(val response: List<RawStandingLeagueWrapper>)

data class ApiTime(
    val time_id: Int,
    val nome_popular: String,
    val sigla: String,
    val escudo: String
)

data class ApiJogador(
    val atleta_id: Int,
    val nome_popular: String,
    val apelido: String,
    val posicao: String,
    val camisa: Int?,
    val foto: String?,
    val data_nascimento: String?,
    val local_nascimento: String?,
    val altura: Double?,
    val peso: Double?,
    val pe: String?,
    val historia: String? = null
)

data class ApiPartida(
    val partida_id: Int,
    val data_realizacao: String,
    val hora_realizacao: String,
    val placar: String?,
    val status: String,
    val time_mandante: ApiTime,
    val time_visitante: ApiTime,
    val placar_mandante: Int?,
    val placar_visitante: Int?,
    val campeonato: ApiCampeonato
)

data class ApiCampeonato(
    val campeonato_id: Int,
    val nome: String,
    val slug: String
)

data class ApiClassificacao(
    val posicao: Int,
    val time: ApiTime,
    val pontos: Int,
    val jogos: Int,
    val vitorias: Int,
    val empates: Int,
    val derrotas: Int,
    val gols_pro: Int,
    val gols_contra: Int,
    val saldo_gols: Int
)

data class RawSquadPlayer(
    val id: Int,
    val name: String,
    val age: Int?,
    val number: Int?,
    val position: String?,
    val photo: String?
)
 
data class RawSquadTeamItem(
    val team: RawTeam,
    val players: List<RawSquadPlayer>
)





interface ApiFootballService {

    @GET("fixtures")
    suspend fun getPartidas(
        @Header("x-apisports-key") apiKey: String,
        @Query("team") teamId: Int,
        @Query("season") season: Int,
        @Query("league") leagueId: Int = 71,  // ← ADICIONE ISSO
        @Query("last") last: Int = 10   // últimas 10 partidas
    ): RawFixtureResponse

    @GET("fixtures")
    suspend fun getProximasPartidas(
        @Header("x-apisports-key") apiKey: String,
        @Query("team") teamId: Int,
        @Query("season") season: Int,
        @Query("league") leagueId: Int = 71,
        @Query("next") next: Int = 5    // próximas 5 partidas
    ): RawFixtureResponse

    @GET("players/squads")
    suspend fun getElenco(
        @Header("x-apisports-key") apiKey: String,
        @Query("team") teamId: Int
    ): RawSquadResponse

    @GET("standings")
    suspend fun getClassificacao(
        @Header("x-apisports-key") apiKey: String,
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): RawStandingsResponse

    companion object {
        private const val BASE_URL = "https://v3.football.api-sports.io/"

        fun create(): ApiFootballService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiFootballService::class.java)
        }
    }
}