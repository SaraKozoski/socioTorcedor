package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.theme.BebasNeue

private val CardSecundarioFundo = Color(0xCC482B2B)
private val CardPrincipalFundo = Color(0xFF2A0A0A)

@Composable
fun ResultsSection() {
    val partidaFutura = partidasMock.firstOrNull { it.futura }
    val partidaPrincipal = partidasMock.firstOrNull { it.aoVivo }
        ?: partidasMock.firstOrNull { !it.futura && !it.aoVivo }
    val partidasSecundarias = partidasMock
        .filter { !it.futura && !it.aoVivo }
        .drop(if (partidaPrincipal != null && !partidaPrincipal.aoVivo) 1 else 0)

    Column {
        Text(
            text = "ÚLTIMOS RESULTADOS",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = BebasNeue,
            color = HomeColors.TextoBranco,
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            if (partidaFutura != null) {
                item { CardPartidaFutura(partida = partidaFutura) }
            }
            if (partidaPrincipal != null) {
                item { CardPartidaPrincipal(partida = partidaPrincipal) }
            }
            items(partidasSecundarias) { partida ->
                CardPartidaSecundaria(partida = partida)
            }
        }
    }
}

@Composable
fun CardPartidaFutura(partida: Partida) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardSecundarioFundo)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = null,
            modifier = Modifier
                .requiredSize(140.dp)
                .align(Alignment.Center),
            alpha = 0.5f,
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = partida.data,
                color = HomeColors.TextoCinza,
                fontSize = 10.sp,
                fontFamily = BebasNeue,
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                EscudoTime(sigla = partida.timeCasa, fundo = HomeColors.Cards1, tamanho = 32)
                Text(
                    text = "VS",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BebasNeue
                )
                EscudoTime(sigla = partida.timeVisitante, fundo = HomeColors.CardClaro, tamanho = 32)
            }

            Button(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HomeColors.FundoCard3),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                Text(
                    text = "COMPRE AQUI",
                    color = Color.White,
                    fontFamily = BebasNeue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun CardPartidaPrincipal(partida: Partida) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardPrincipalFundo)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = null,
            modifier = Modifier
                .requiredSize(180.dp)
                .align(Alignment.Center),
            alpha = 0.5f,
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                if (partida.aoVivo) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(4.dp))
                            .background(HomeColors.Cards1)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "AO VIVO",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = BebasNeue
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (partida.aoVivo) Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = partida.competicao,
                        color = HomeColors.TextoBranco,
                        fontSize = 12.sp,
                        fontFamily = BebasNeue,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = partida.data,
                        color = HomeColors.TextoCinza,
                        fontSize = 11.sp,
                        fontFamily = BebasNeue,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                EscudoTime(sigla = partida.timeCasa, fundo = HomeColors.Cards1, tamanho = 48)
                Text(
                    text = "${partida.placarCasa}  X  ${partida.placarVisitante}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BebasNeue
                )
                EscudoTime(sigla = partida.timeVisitante, fundo = HomeColors.CardClaro, tamanho = 48)
            }

            Button(
                onClick = { },
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(2.dp, HomeColors.DetalhesCard1),
                colors = ButtonDefaults.buttonColors(containerColor = HomeColors.Cards1),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "APOSTE AQUI",
                    color = Color.White,
                    fontFamily = BebasNeue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun CardPartidaSecundaria(partida: Partida) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardSecundarioFundo)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = null,
            modifier = Modifier
                .requiredSize(140.dp)
                .align(Alignment.Center),
            alpha = 0.5f,
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = partida.data,
                color = HomeColors.TextoCinza,
                fontSize = 10.sp,
                fontFamily = BebasNeue,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                EscudoTime(sigla = partida.timeCasa, fundo = HomeColors.Cards1, tamanho = 32)
                Text(
                    text = "${partida.placarCasa}  ${partida.placarVisitante}",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BebasNeue
                )
                EscudoTime(sigla = partida.timeVisitante, fundo = HomeColors.CardClaro, tamanho = 32)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    partida.gols.take(2).forEach { gol ->
                        Text(
                            text = "• $gol",
                            color = HomeColors.TextoCinza,
                            fontSize = 9.sp,
                            fontFamily = BebasNeue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EscudoTime(sigla: String, fundo: Color, tamanho: Int) {
    Box(
        modifier = Modifier
            .size(tamanho.dp)
            .clip(CircleShape)
            .background(fundo),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sigla,
            color = if (fundo == HomeColors.CardClaro) HomeColors.Preto else Color.White,
            fontSize = (tamanho / 4).sp,
            fontWeight = FontWeight.Bold,
            fontFamily = BebasNeue,
            textAlign = TextAlign.Center
        )
    }
}