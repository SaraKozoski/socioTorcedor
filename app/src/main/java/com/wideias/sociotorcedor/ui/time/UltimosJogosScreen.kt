package com.wideias.sociotorcedor.ui.time

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.data.remote.ApiPartida
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

private val ContainerFundo  = Color(0xFF1C0D0D)
private val CardAoVivoFundo = Color(0xFF7C1010)
private val CardAoVivoBorda = Color(0xFFA01414)
private val CardSecundFundo = Color(0xFF3D2020)
private val CardSecundBorda = Color(0xFF5A2A2A)
private val BotaoFundo      = Color(0xFF9E1A1A)

private const val TIME_CASA_ID = 134

@Composable
fun UltimosJogosScreen(viewModel: TimeViewModel = viewModel()) {

    val state by viewModel.partidas.collectAsState()

    LaunchedEffect(Unit) { viewModel.carregarPartidas() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(ContainerFundo)
    ) {
        when (state) {

            is UiState.Carregando -> {
                CircularProgressIndicator(
                    color = HomeColors.Cards1,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Erro -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Erro ao carregar", color = Color.White, fontFamily = BebasNeue, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toque para tentar novamente",
                        color = HomeColors.Cards1,
                        fontFamily = BebasNeue,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { viewModel.carregarPartidas() }
                    )
                }
            }

            is UiState.Sucesso -> {
                val partidas = (state as UiState.Sucesso<List<ApiPartida>>).dados

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(partidas) { partida ->
                        when (partida.status) {
                            "ao_vivo", "intervalo" -> CardPartidaAoVivo(partida)
                            "agendado"             -> CardPartidaFutura(partida)
                            else                   -> CardPartidaPassada(partida)
                        }
                    }
                }
            }
        }
    }
}

// ── Card AO VIVO ──────────────────────────────────────────
@Composable
fun CardPartidaAoVivo(partida: ApiPartida) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardAoVivoFundo)
            .border(1.dp, CardAoVivoBorda, RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_branco),
            contentDescription = null,
            modifier = Modifier.size(180.dp).align(Alignment.Center),
            alpha = 0.08f,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(HomeColors.Cards1)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color.White))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "AO VIVO", color = Color.White, fontFamily = BebasNeue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = partida.campeonato.nome, color = Color.White, fontFamily = BebasNeue, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EscudoPartida(timeId = partida.time_mandante.time_id, escudoUrl = partida.time_mandante.escudo, sigla = partida.time_mandante.sigla)
                Text(
                    text = "${partida.placar_mandante ?: 0}  X  ${partida.placar_visitante ?: 0}",
                    color = Color.White, fontFamily = BebasNeue, fontSize = 32.sp, fontWeight = FontWeight.Bold
                )
                EscudoPartida(timeId = partida.time_visitante.time_id, escudoUrl = partida.time_visitante.escudo, sigla = partida.time_visitante.sigla)
            }
        }
    }
}

// ── Card FUTURO ───────────────────────────────────────────
@Composable
fun CardPartidaFutura(partida: ApiPartida) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardSecundFundo)
            .border(1.dp, CardSecundBorda, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = partida.campeonato.nome, color = HomeColors.TextoCinza, fontFamily = BebasNeue, fontSize = 12.sp)
            Text(text = "${partida.data_realizacao} • ${partida.hora_realizacao}", color = Color.White, fontFamily = BebasNeue, fontSize = 14.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EscudoPartida(timeId = partida.time_mandante.time_id, escudoUrl = partida.time_mandante.escudo, sigla = partida.time_mandante.sigla)
                Text(text = "VS", color = Color.White, fontFamily = BebasNeue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                EscudoPartida(timeId = partida.time_visitante.time_id, escudoUrl = partida.time_visitante.escudo, sigla = partida.time_visitante.sigla)
            }
            Button(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BotaoFundo),
                border = BorderStroke(1.5.dp, CardSecundBorda),
                modifier = Modifier.height(37.dp).padding(horizontal = 20.dp)
            ) {
                Text(text = "COMPRE AQUI", color = Color.White, fontFamily = BebasNeue, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

// ── Card PASSADO ──────────────────────────────────────────
@Composable
fun CardPartidaPassada(partida: ApiPartida) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardSecundFundo)
            .border(1.dp, CardSecundBorda, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = partida.campeonato.nome, color = HomeColors.TextoCinza, fontFamily = BebasNeue, fontSize = 12.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EscudoPartida(timeId = partida.time_mandante.time_id, escudoUrl = partida.time_mandante.escudo, sigla = partida.time_mandante.sigla)
                Text(
                    text = "${partida.placar_mandante ?: 0}  X  ${partida.placar_visitante ?: 0}",
                    color = Color.White, fontFamily = BebasNeue, fontSize = 26.sp, fontWeight = FontWeight.Bold
                )
                EscudoPartida(timeId = partida.time_visitante.time_id, escudoUrl = partida.time_visitante.escudo, sigla = partida.time_visitante.sigla)
            }
            Text(text = partida.data_realizacao, color = HomeColors.TextoCinza, fontFamily = BebasNeue, fontSize = 11.sp)
            Button(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BotaoFundo),
                border = BorderStroke(1.5.dp, CardSecundBorda),
                modifier = Modifier.fillMaxWidth(0.55f).height(36.dp)
            ) {
                Text(text = "VER DE NOVO", color = Color.White, fontFamily = BebasNeue, fontSize = 13.sp)
            }
        }
    }
}

// ── Escudo ────────────────────────────────────────────────
@Composable
fun EscudoPartida(timeId: Int, escudoUrl: String, sigla: String) {
    Box(
        modifier = Modifier.size(52.dp),
        contentAlignment = Alignment.Center
    ) {
        if (timeId == TIME_CASA_ID) {
            Image(
                painter = painterResource(id = R.drawable.logo_branco),
                contentDescription = null,
                modifier = Modifier.requiredSize(72.dp),
                contentScale = ContentScale.Fit
            )
        } else if (escudoUrl.isNotEmpty()) {
            AsyncImage(
                model = escudoUrl,
                contentDescription = sigla,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(HomeColors.CardClaro),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sigla,
                    color = HomeColors.Preto,
                    fontFamily = BebasNeue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}