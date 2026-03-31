package com.wideias.sociotorcedor.ui.time

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.data.remote.ApiClassificacao
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

private val FundoTabela    = Color(0xFF2A1515)
private val FundoHeader    = Color(0xFF3D1E1E)
private val FundoLinha     = Color(0xFF221010)
private val FundoLinhaAlt  = Color(0xFF1C0D0D)
private val DestaqueLinha  = Color(0xFF7C1010)


@Composable
fun ClassificacaoScreen(viewModel: TimeViewModel = viewModel()) {
    val state by viewModel.classificacao.collectAsState()
   
    LaunchedEffect(Unit) {
        viewModel.carregarClassificacao()
    }

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
                    Text(
                        text = "Erro ao carregar",
                        color = Color.White,
                        fontFamily = BebasNeue,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toque para tentar novamente",
                        color = HomeColors.Cards1,
                        fontFamily = BebasNeue,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable { viewModel.carregarClassificacao() }
                    )
                }
            }

            is UiState.Sucesso -> {
                val lista = (state as UiState.Sucesso<List<ApiClassificacao>>).dados

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .border(1.dp, HomeColors.Cards1, RoundedCornerShape(50.dp))
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "BRASILEIRÃO 2026",
                                color = Color.White,
                                fontFamily = BebasNeue,
                                fontSize = 13.sp
                            )
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(FundoHeader)
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Posição", color = HomeColors.TextoCinza, fontFamily = BebasNeue, fontSize = 11.sp, modifier = Modifier.width(80.dp))
                            Spacer(modifier = Modifier.weight(1f))
                            listOf("Pts", "PJ", "VIT", "GM", "SG").forEach { col ->
                                Text(text = col, color = HomeColors.TextoCinza, fontFamily = BebasNeue, fontSize = 11.sp, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                            }
                        }
                    }

                    itemsIndexed(lista) { index, item ->
                        val isDestaque = item.time.time_id == 134 
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    when {
                                        isDestaque -> DestaqueLinha
                                        index % 2 == 0 -> FundoLinha
                                        else -> FundoLinhaAlt
                                    }
                                )
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${item.posicao}",
                                color = Color.White,
                                fontFamily = BebasNeue,
                                fontSize = 13.sp,
                                modifier = Modifier.width(20.dp),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                            AsyncImage(
                                model = item.time.escudo,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                        
                            Text(
                                text = item.time.nome_popular,
                                color = if (isDestaque) Color.White else Color.White.copy(alpha = 0.85f),
                                fontFamily = BebasNeue,
                                fontSize = 12.sp,
                                modifier = Modifier.width(90.dp),
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            listOf(
                                item.pontos,
                                item.jogos,
                                item.vitorias,
                                item.gols_pro,
                                item.saldo_gols
                            ).forEach { valor ->
                                Text(
                                    text = "$valor",
                                    color = Color.White,
                                    fontFamily = BebasNeue,
                                    fontSize = 12.sp,
                                    modifier = Modifier.width(32.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.White.copy(alpha = 0.05f))
                        )
                    }
                }
            }
        }
    }
}
     
