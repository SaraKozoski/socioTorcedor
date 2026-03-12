package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wideias.sociotorcedor.ui.theme.BebasNeue

@Composable
fun BetCard(
    nomeUsuario: String = "N/A",
    saldoAtual: String = "R\$0,00",
    pontosAtual: Int = 0,
    pontosMaximo: Int = 250,
    onAdicionarCredito: () -> Unit = {}
) {
    val valores = listOf(0, 50, 100, 150, 200, 250)
    val indexAtual = valores.indexOfLast { it <= pontosAtual }.coerceAtLeast(0)
    val progresso = (pontosAtual.toFloat() / pontosMaximo.toFloat()).coerceIn(0f, 1f)

    // Verifica se o valor é quebrado (não está exatamente em nenhum marcador)
    val isValorQuebrado = !valores.contains(pontosAtual) && pontosAtual > 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {

            // ── Parte superior clara ──────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HomeColors.CardClaro)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = nomeUsuario,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = BebasNeue,
                        color = HomeColors.Preto
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "•",
                        fontSize = 16.sp,
                        color = HomeColors.Preto
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Fogo",
                        tint = HomeColors.Vermelho,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = saldoAtual,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = BebasNeue,
                        color = HomeColors.Preto
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(HomeColors.Vermelho)
                            .clickable { onAdicionarCredito() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // ── Parte inferior vermelha com barra de progresso ──
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HomeColors.VermelhoEscuro)
                    .padding(horizontal = 12.dp, vertical = 14.dp)
            ) {

                // ── Número de pontos flutuante acima da barra ──
                if (isValorQuebrado) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progresso)
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clip(RoundedCornerShape(4.dp))                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "$pontosAtual pts",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                // ── Barra de progresso ──
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                ) {
                    // Trilha de fundo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                    )

                    // Preenchimento com borda branca até o valor atual
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progresso)
                            .height(36.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .border(
                                width = 1.5.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(50.dp)
                            )
                    )

                    // Labels dos valores sobre a barra
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        valores.forEachIndexed { index, valor ->
                            val isAtual = index == indexAtual && !isValorQuebrado
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (valor == 0) "Opts" else "$valor",
                                    color = if (index <= indexAtual) Color.White
                                            else Color.White.copy(alpha = 0.5f),
                                    fontWeight = if (isAtual) FontWeight.Bold
                                                 else FontWeight.Normal,
                                    fontFamily = BebasNeue,
                                    fontSize = if (isAtual) 15.sp else 13.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}