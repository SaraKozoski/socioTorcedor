package com.wideias.sociotorcedor.ui.planos

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import com.wideias.sociotorcedor.R

// ── Cores das zonas ───────────────────────────────────────
private val CorRedZona    = Color(0xFFBA0000)
private val CorGoldZona   = Color(0xFFFFD700)
private val CorBlackZona  = Color(0xFF111111)

@Composable
fun StadiumMap(
    planoSelecionado: TipoPlano,
    onZonaSelecionada: (TipoPlano) -> Unit
) {
    // Alpha de cada zona — zona selecionada brilha, outras somem
    val alphaRed by animateFloatAsState(
        targetValue = when (planoSelecionado) {
            TipoPlano.RED    -> 0.75f
            TipoPlano.NENHUM -> 0f
            else             -> 0.55f
        },
        animationSpec = tween(300), label = "alphaRed"
    )
    val alphaGold by animateFloatAsState(
        targetValue = when (planoSelecionado) {
            TipoPlano.GOLD   -> 0.75f
            TipoPlano.NENHUM -> 0f
            else             -> 0.55f
        },
        animationSpec = tween(300), label = "alphaGold"
    )
    val alphaBlack by animateFloatAsState(
        targetValue = when (planoSelecionado) {
            TipoPlano.BLACK  -> 0.75f
            TipoPlano.NENHUM -> 0f
            else             -> 0.55f
        },
        animationSpec = tween(300), label = "alphaBlack"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        // ── Imagem real do estádio ────────────────────────
        Image(
            painter = painterResource(id = R.drawable.estadio_mapa),
            contentDescription = "Mapa do estádio",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        // ── Zonas sobrepostas clicáveis ───────────────────
        // A imagem tem proporção ~0.85 (altura/largura)
        // Usamos BoxWithConstraints para calcular tamanhos relativos

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val w = maxWidth
            val h = w * 0.85f // proporção da imagem

            Box(modifier = Modifier.size(w, h)) {

                // ── ZONA RED — cantos e faixas externas ──
                // Canto superior esquerdo
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.0f, y = h * 0.08f)
                        .width(w * 0.14f)
                        .height(h * 0.65f),
                    cor = CorRedZona,
                    alpha = alphaRed,
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                    onClick = { onZonaSelecionada(TipoPlano.RED) }
                )
                // Canto superior direito
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.86f, y = h * 0.08f)
                        .width(w * 0.14f)
                        .height(h * 0.65f),
                    cor = CorRedZona,
                    alpha = alphaRed,
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                    onClick = { onZonaSelecionada(TipoPlano.RED) }
                )
                // Faixa superior
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.14f, y = h * 0.04f)
                        .width(w * 0.72f)
                        .height(h * 0.12f),
                    cor = CorRedZona,
                    alpha = alphaRed,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    onClick = { onZonaSelecionada(TipoPlano.RED) }
                )
                // Faixa inferior
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.14f, y = h * 0.78f)
                        .width(w * 0.72f)
                        .height(h * 0.14f),
                    cor = CorRedZona,
                    alpha = alphaRed,
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
                    onClick = { onZonaSelecionada(TipoPlano.RED) }
                )

                // ── ZONA GOLD — laterais internas e topo interno ──
                // Lateral esquerda interna
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.14f, y = h * 0.16f)
                        .width(w * 0.11f)
                        .height(h * 0.55f),
                    cor = CorGoldZona,
                    alpha = alphaGold,
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onZonaSelecionada(TipoPlano.GOLD) }
                )
                // Lateral direita interna
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.75f, y = h * 0.16f)
                        .width(w * 0.11f)
                        .height(h * 0.55f),
                    cor = CorGoldZona,
                    alpha = alphaGold,
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onZonaSelecionada(TipoPlano.GOLD) }
                )
                // Faixa superior interna (setor 115-119)
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.25f, y = h * 0.16f)
                        .width(w * 0.50f)
                        .height(h * 0.10f),
                    cor = CorGoldZona,
                    alpha = alphaGold,
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onZonaSelecionada(TipoPlano.GOLD) }
                )
                // Faixa inferior interna (setor 103-108)
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.25f, y = h * 0.65f)
                        .width(w * 0.50f)
                        .height(h * 0.10f),
                    cor = CorGoldZona,
                    alpha = alphaGold,
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onZonaSelecionada(TipoPlano.GOLD) }
                )

                // ── ZONA BLACK — faixa VIP inferior ──────
                ZonaClicavel(
                    modifier = Modifier
                        .offset(x = w * 0.14f, y = h * 0.70f)
                        .width(w * 0.72f)
                        .height(h * 0.06f),
                    cor = CorBlackZona,
                    alpha = alphaBlack,
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onZonaSelecionada(TipoPlano.BLACK) }
                )
            }
        }
    }
}

// ── Componente reutilizável de zona clicável ──────────────
@Composable
fun ZonaClicavel(
    modifier: Modifier,
    cor: Color,
    alpha: Float,
    shape: RoundedCornerShape,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .alpha(alpha)
            .background(cor)
            .clickable { onClick() }
    )
}