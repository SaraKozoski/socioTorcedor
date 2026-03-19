package com.wideias.sociotorcedor.ui.planos

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

@Composable
fun PlanoDetalheScreen(tipoPlano: String, navController: NavController) {
    val plano = planosInfo.firstOrNull { it.tipo.name == tipoPlano } ?: planosInfo.first()
    val scrollState = rememberScrollState()
    var pullAcumulado by remember { mutableStateOf(0f) }
    val limiteParaVoltar = 120f
    val progressoPull = (pullAcumulado / limiteParaVoltar).coerceIn(0f, 1f)

   var voltando by remember { mutableStateOf(false) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0) pullAcumulado = 0f
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (scrollState.value == 0 && available.y > 0) {
                    pullAcumulado = (pullAcumulado + available.y).coerceAtLeast(0f)
                    if (pullAcumulado >= limiteParaVoltar && !voltando) {
                        voltando = true
                        pullAcumulado = 0f
                        navController.popBackStack()
                    }
                } else if (available.y <= 0 && consumed.y != 0f) {
                    pullAcumulado = 0f
                }
                return Offset.Zero
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.Fundo)
            .nestedScroll(nestedScrollConnection)
            .verticalScroll(scrollState)
    ) {

        // ── Indicador pull down ───────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width((32 + progressoPull * 32).dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.White.copy(alpha = 0.15f + progressoPull * 0.5f))
                )
                if (progressoPull > 0.05f) {
                    Text(
                        text = if (progressoPull > 0.6f) "↓ Solte para voltar" else "↓ Puxe para voltar",
                        color = Color.White.copy(alpha = progressoPull * 0.8f),
                        fontFamily = BebasNeue,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // ── Cards dos planos ──────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            planosInfo.forEach { p ->
                val selecionado = p.tipo == plano.tipo
                Box(
                    modifier = Modifier
                        .scale(if (selecionado) 1.15f else 0.88f)
                        .width(100.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(p.cor)
                        .then(
                            if (selecionado) Modifier.border(2.dp, Color.White, RoundedCornerShape(16.dp))
                            else Modifier
                        )
                        .clickable {
                            if (!selecionado) {
                                navController.navigate("plano_detalhe/${p.tipo.name}") {
                                    popUpTo("plano_detalhe/{tipoPlano}") { inclusive = true }
                                }
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_clube),
                        contentDescription = null,
                        modifier = Modifier.requiredSize(120.dp).align(Alignment.Center),
                        alpha = 0.2f
                    )
                    Column(
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter = painterResource(id = R.drawable.logo_clube), contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = p.nome, color = Color.White, fontFamily = BebasNeue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Image(painter = painterResource(id = R.drawable.logo_clube), contentDescription = null, modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally))
                    }
                }
            }
        }

        // ── Especificações ────────────────────────────────
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Text(text = "ESPECIFICAÇÕES", color = Color.White, fontFamily = BebasNeue, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = plano.especificacoes, color = Color.White.copy(alpha = 0.85f), fontFamily = BebasNeue, fontSize = 13.sp, lineHeight = 18.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "DIFERENCIAIS", color = Color.White, fontFamily = BebasNeue, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))

            plano.diferenciais.forEach { (esquerda, direita) ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(plano.cor))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = esquerda, color = Color.White.copy(alpha = 0.85f), fontFamily = BebasNeue, fontSize = 13.sp)
                    }
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(plano.cor))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = direita, color = Color.White.copy(alpha = 0.85f), fontFamily = BebasNeue, fontSize = 13.sp, textAlign = TextAlign.End)
                    }
                }
                HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Preço + botão ─────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(plano.cor)
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = plano.parcelas, color = Color.White.copy(alpha = 0.9f), fontFamily = BebasNeue, fontSize = 14.sp)
                Text(text = "R$ ${plano.preco}", color = Color.White, fontFamily = BebasNeue, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth(0.7f).height(46.dp)
                ) {
                    Text(text = "ASSINAR AGORA", color = plano.cor, fontFamily = BebasNeue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}