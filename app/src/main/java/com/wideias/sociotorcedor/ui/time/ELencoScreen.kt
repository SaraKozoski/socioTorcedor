package com.wideias.sociotorcedor.ui.time

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.data.remote.ApiJogador
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

private val CardJogadorFundo = Color(0xFF2A1515)
private val CardJogadorBorda = Color(0xFF5A2A2A)

private fun traduzirPosicao(posicao: String) = when (posicao.lowercase()) {
    "goalkeeper"  -> "GOLEIROS"
    "defender"    -> "DEFENSORES"
    "midfielder"  -> "MEIO-CAMPISTAS"
    "attacker"    -> "ATACANTES"
    else          -> posicao.uppercase()
}

@Composable
fun ElencoScreen(
    navController: NavController,
    viewModel: TimeViewModel = viewModel()
) {
    val state by viewModel.elenco.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C0D0D))
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
                    Text(text = "Erro ao carregar", color = Color.White, fontFamily = BebasNeue, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toque para tentar novamente",
                        color = HomeColors.Cards1,
                        fontFamily = BebasNeue,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { viewModel.carregarElenco() }
                    )
                }
            }

            is UiState.Sucesso -> {
                val jogadores = (state as UiState.Sucesso<List<ApiJogador>>).dados
                val porPosicao = jogadores.groupBy { traduzirPosicao(it.posicao) }
                val ordem = listOf("GOLEIROS", "DEFENSORES", "MEIO-CAMPISTAS", "ATACANTES")

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ordem.forEach { posicao ->
                        val lista = porPosicao[posicao] ?: return@forEach

                        item(span = { GridItemSpan(3) }) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(50.dp))
                                    .border(1.dp, HomeColors.Cards1, RoundedCornerShape(50.dp))
                                    .padding(horizontal = 16.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = posicao,
                                    color = Color.White,
                                    fontFamily = BebasNeue,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        items(lista) { jogador ->
                            CardJogador(
                                jogador = jogador,
                                onClick = {
                                    navController.navigate("jogador/${jogador.atleta_id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardJogador(jogador: ApiJogador, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CardJogadorFundo)
            .border(1.dp, CardJogadorBorda, RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color(0xFF3D2020))
            ) {
                AsyncImage(
                    model = jogador.foto,
                    contentDescription = jogador.nome_popular,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${jogador.camisa ?: ""}",
                    color = Color.White.copy(alpha = 0.9f),
                    fontFamily = BebasNeue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                )
            }

            Text(
                text = jogador.apelido.ifEmpty { jogador.nome_popular },
                color = Color.White,
                fontFamily = BebasNeue,
                fontSize = 11.sp,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
    }
}