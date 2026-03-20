package com.wideias.sociotorcedor.ui.planos

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanosScreen(navController: NavController) {
    var planoSelecionado by remember { mutableStateOf(TipoPlano.NENHUM) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
    var mostrarSheet by remember { mutableStateOf(false) }
    val planoAtual = planosInfo.firstOrNull { it.tipo == planoSelecionado }

    // Sheet expandido → navega imediatamente sem delay
    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == SheetValue.Expanded && planoAtual != null) {
            val tipo = planoAtual.tipo.name
            navController.navigate("plano_detalhe/$tipo")
            scope.launch {
                sheetState.hide()
                mostrarSheet = false
            }
        }
    }

    val imagemEstadio = when (planoSelecionado) {
        TipoPlano.RED    -> R.drawable.estadio_red
        TipoPlano.GOLD   -> R.drawable.estadio_gold
        TipoPlano.BLACK  -> R.drawable.estadio_black
        TipoPlano.NENHUM -> R.drawable.estadio_default
    }

    if (mostrarSheet && planoAtual != null) {
        ModalBottomSheet(
            onDismissRequest = {
                mostrarSheet = false
                planoSelecionado = TipoPlano.NENHUM
            },
            sheetState = sheetState,
            containerColor = HomeColors.CardEscuro,
            scrimColor = Color.Black.copy(alpha = 0.6f),
            dragHandle = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.White.copy(alpha = 0.3f))
                    )
                    Text(
                        text = "↑ Arraste para abrir detalhes",
                        color = Color.White.copy(alpha = 0.4f),
                        fontFamily = BebasNeue,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
                    )
                }
            }
        ) {
            PlanoBottomSheetContent(
                plano = planoAtual,
                onAssinar = {
                    val tipo = planoAtual.tipo.name
                    scope.launch { sheetState.hide() }
                    mostrarSheet = true
                    navController.navigate("plano_detalhe/$tipo")
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.Fundo)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            planosInfo.forEach { plano ->
                val scale by animateFloatAsState(
                    targetValue = when {
                        planoSelecionado == TipoPlano.NENHUM -> 1f
                        planoSelecionado == plano.tipo       -> 1.15f
                        else                                 -> 0.88f
                    },
                    animationSpec = tween(300),
                    label = "scale_${plano.nome}"
                )
                CardPlanoCompacto(
                    plano = plano,
                    selecionado = planoSelecionado == plano.tipo,
                    scale = scale,
                    onClick = {
                        planoSelecionado = plano.tipo
                        mostrarSheet = true
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    if (planoSelecionado != TipoPlano.NENHUM) mostrarSheet = true
                }
        ) {
            Crossfade(targetState = imagemEstadio, animationSpec = tween(400), label = "estadio") { imagem ->
                Image(
                    painter = painterResource(id = imagem),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
            if (planoSelecionado == TipoPlano.NENHUM) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Toque em um plano para ver os setores", color = Color.White, fontFamily = BebasNeue, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            planosInfo.forEach { plano ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        planoSelecionado = if (planoSelecionado == plano.tipo) TipoPlano.NENHUM else plano.tipo
                        if (planoSelecionado == plano.tipo) mostrarSheet = true
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(plano.cor)
                            .then(if (planoSelecionado == plano.tipo) Modifier.border(2.dp, Color.White, CircleShape) else Modifier)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = plano.nome, color = Color.White, fontFamily = BebasNeue, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ── Bottom Sheet Content ──────────────────────────────────
@Composable
fun PlanoBottomSheetContent(plano: PlanoInfo, onAssinar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 32.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(plano.cor), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.logo_clube), contentDescription = null, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Plano ${plano.nome}", color = plano.cor, fontFamily = BebasNeue, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = plano.parcelas, color = Color.White.copy(alpha = 0.7f), fontFamily = BebasNeue, fontSize = 13.sp)
            }
        }

        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
        Spacer(modifier = Modifier.height(12.dp))

        plano.diferenciais.forEach { (esquerda, direita) ->
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(text = "✓ ", color = plano.cor, fontFamily = BebasNeue, fontSize = 13.sp)
                    Text(text = esquerda, color = Color.White, fontFamily = BebasNeue, fontSize = 13.sp)
                }
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                    Text(text = "✓ ", color = plano.cor, fontFamily = BebasNeue, fontSize = 13.sp)
                    Text(text = direita, color = Color.White, fontFamily = BebasNeue, fontSize = 13.sp, textAlign = TextAlign.End)
                }
            }
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(plano.cor).padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = plano.parcelas, color = Color.White.copy(alpha = 0.9f), fontFamily = BebasNeue, fontSize = 14.sp)
                Text(text = "R$ ${plano.preco}", color = Color.White, fontFamily = BebasNeue, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onAssinar,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth(0.7f).height(46.dp)
                ) {
                    Text(text = "ASSINAR AGORA", color = plano.cor, fontFamily = BebasNeue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

// ── Card compacto ─────────────────────────────────────────
@Composable
fun CardPlanoCompacto(plano: PlanoInfo, selecionado: Boolean, scale: Float, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .scale(scale)
            .width(100.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(plano.cor)
            .then(if (selecionado) Modifier.border(2.dp, Color.White, RoundedCornerShape(16.dp)) else Modifier)
            .clickable { onClick() }
    ) {
        Image(painter = painterResource(id = R.drawable.logo_clube), contentDescription = null, modifier = Modifier.requiredSize(120.dp).align(Alignment.Center), alpha = 0.2f)
        Column(modifier = Modifier.fillMaxSize().padding(8.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.logo_clube), contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = plano.nome, color = Color.White, fontFamily = BebasNeue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Image(painter = painterResource(id = R.drawable.logo_clube), contentDescription = null, modifier = Modifier.size(60.dp).align(Alignment.CenterHorizontally))
        }
    }
}