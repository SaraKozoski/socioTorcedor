package com.wideias.sociotorcedor.ui.time

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.data.remote.ApiJogador
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

private val FundoDetalhe   = Color(0xFF1C0D0D)
private val DivisorCor     = Color(0xFF3D2020)
private val BadgeFundo     = Color(0xFF3D2020)
private val BadgeBorda     = Color(0xFF7C1010)

@Composable
fun JogadorDetalheScreen(
    atletaId: Int,
    navController: NavController,
    viewModel: TimeViewModel = viewModel()
) {
    val state by viewModel.elenco.collectAsState()

    // Busca primeiro na API; se não achar, cai para o mock
    val jogador: ApiJogador? =
        (state as? UiState.Sucesso<List<ApiJogador>>)
            ?.dados?.firstOrNull { it.atleta_id == atletaId }
            ?: jogadoresMock.firstOrNull { it.atleta_id == atletaId }

    if (jogador == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FundoDetalhe),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Jogador não encontrado",
                color = Color.White,
                fontFamily = BebasNeue,
                fontSize = 16.sp
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoDetalhe)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Foto com gradiente ────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            if (jogador.foto != null) {
                AsyncImage(
                    model = jogador.foto,
                    contentDescription = jogador.nome_popular,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder com fundo e logo do clube quando não tem foto
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF3D2020)),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.logo_branco),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        alpha = 0.15f
                    )
                }
            }

            // Gradiente sobre a foto
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                FundoDetalhe.copy(alpha = 0.7f),
                                FundoDetalhe
                            ),
                            startY = 80f
                        )
                    )
            )

            // Número e nome sobre o gradiente
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                if (jogador.camisa != null) {
                    Text(
                        text = "${jogador.camisa}",
                        color = HomeColors.Cards1.copy(alpha = 0.8f),
                        fontFamily = BebasNeue,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = jogador.nome_popular.uppercase(),
                    color = Color.White,
                    fontFamily = BebasNeue,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                // Badge de posição
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(BadgeFundo)
                        .border(1.dp, BadgeBorda, RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = traduzirPosicaoDetalhe(jogador.posicao),
                        color = Color.White,
                        fontFamily = BebasNeue,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // ── Dados do jogador ──────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Nascimento
            Row(modifier = Modifier.fillMaxWidth()) {
                InfoItem(
                    titulo = "DATA DE NASCIMENTO",
                    valor  = jogador.data_nascimento ?: "-",
                    modifier = Modifier.weight(1f)
                )
                InfoItem(
                    titulo = "LOCAL DE NASCIMENTO",
                    valor  = jogador.local_nascimento ?: "-",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = DivisorCor)
            Spacer(modifier = Modifier.height(16.dp))

            // Físico
            Row(modifier = Modifier.fillMaxWidth()) {
                InfoItem(
                    titulo = "ALTURA",
                    valor  = jogador.altura?.let { "${it}m" } ?: "-",
                    modifier = Modifier.weight(1f)
                )
                InfoItem(
                    titulo = "PESO",
                    valor  = jogador.peso?.let { "${it} kg" } ?: "-",
                    modifier = Modifier.weight(1f)
                )
                InfoItem(
                    titulo = "PÉ",
                    valor  = jogador.pe?.replaceFirstChar { it.uppercase() } ?: "-",
                    modifier = Modifier.weight(1f)
                )
            }

            // História
            if (!jogador.historia.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = DivisorCor)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "HISTÓRIA",
                    color = Color.White,
                    fontFamily = BebasNeue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = jogador.historia,
                    color = Color.White.copy(alpha = 0.85f),
                    fontFamily = BebasNeue,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


private fun traduzirPosicaoDetalhe(posicao: String) = when (posicao.lowercase()) {
    "goalkeeper" -> "GOLEIRO"
    "defender"   -> "DEFENSOR"
    "midfielder" -> "MEIO-CAMPO"
    "attacker"   -> "ATACANTE"
    else         -> posicao.uppercase()
}

@Composable
fun InfoItem(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(end = 8.dp)) {
        Text(
            text  = titulo,
            color = HomeColors.TextoCinza,
            fontFamily = BebasNeue,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text  = valor,
            color = Color.White,
            fontFamily = BebasNeue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}