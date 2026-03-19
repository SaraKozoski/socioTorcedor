package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Add
import com.wideias.sociotorcedor.ui.home.HomeColors

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

    val isValorQuebrado = !valores.contains(pontosAtual) && pontosAtual > 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {

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
                        tint = HomeColors.Cards1,
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
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(HomeColors.Cards1)
                            .clickable { onAdicionarCredito() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HomeColors.DetalhesCard1)
                    .padding(horizontal = 12.dp, vertical = 14.dp)
            ) {

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
                                    .clip(RoundedCornerShape(4.dp))                      
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
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

                Box(
                    modifier = Modifier.fillMaxWidth().height(36.dp)
                ) {
                
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                    )

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