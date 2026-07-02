package com.wideias.sociotorcedor.ui.ingressos

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.ui.theme.FundoEscuro
import com.wideias.sociotorcedor.ui.theme.AppColors

private val CardEscuro    get() = AppColors.cardDark2
private val TextoSecund   get() = AppColors.textSecondary2
private val VermelhoBotao get() = AppColors.brand
private val AmareloFundo  get() = AppColors.warning
private val VerdeConfirm  get() = AppColors.success
private val BordaCard     get() = AppColors.cardBorder
private val White = Color.White

// ────────────────────────────────────────────────
// Modelo de dados
// ────────────────────────────────────────────────

enum class StatusIngresso { DISPONIVEL, ESGOTADO, EM_BREVE }

data class JogoDisponivel(
    val id          : Int,
    val mandante    : String,
    val visitante   : String,
    val competicao  : String,
    val data        : String,
    val hora        : String,
    val estadio     : String,
    val precoMinimo : Double?,
    val precoOriginal: Double? = null,
    val status      : StatusIngresso,
    val dataAbertura: String? = null   // usado quando status == EM_BREVE
)

// ────────────────────────────────────────────────
// Dados mockados — substitua por chamada à API
// ────────────────────────────────────────────────

private val jogosMock = listOf(
    JogoDisponivel(
        id           = 1,
        mandante     = "Atlético",
        visitante    = "Flamengo",
        competicao   = "Brasileirão · Rodada 14",
        data         = "07 Jun, Sáb",
        hora         = "16h00",
        estadio      = "Arena MRV",
        precoMinimo  = 45.0,
        precoOriginal = 50.0,
        status       = StatusIngresso.DISPONIVEL
    ),
    JogoDisponivel(
        id           = 2,
        mandante     = "Atlético",
        visitante    = "Corinthians",
        competicao   = "Copa do Brasil · Oitavas",
        data         = "12 Jun, Qui",
        hora         = "19h30",
        estadio      = "Arena MRV",
        precoMinimo  = 60.0,
        precoOriginal = 80.0,
        status       = StatusIngresso.DISPONIVEL
    ),
    JogoDisponivel(
        id           = 3,
        mandante     = "Atlético",
        visitante    = "Palmeiras",
        competicao   = "Brasileirão · Rodada 15",
        data         = "15 Jun, Dom",
        hora         = "18h00",
        estadio      = "Arena MRV",
        precoMinimo  = null,
        status       = StatusIngresso.ESGOTADO
    ),
    JogoDisponivel(
        id           = 4,
        mandante     = "Atlético",
        visitante    = "Nacional",
        competicao   = "Libertadores · Fase de Grupos",
        data         = "22 Jun, Dom",
        hora         = "21h30",
        estadio      = "Arena MRV",
        precoMinimo  = null,
        status       = StatusIngresso.EM_BREVE,
        dataAbertura = "18 Jun"
    ),
    JogoDisponivel(
        id           = 5,
        mandante     = "Atlético",
        visitante    = "São Paulo",
        competicao   = "Brasileirão · Rodada 16",
        data         = "28 Jun, Sáb",
        hora         = "17h00",
        estadio      = "Arena MRV",
        precoMinimo  = null,
        status       = StatusIngresso.EM_BREVE,
        dataAbertura = "23 Jun"
    )
)

// ────────────────────────────────────────────────
// Tela principal
// ────────────────────────────────────────────────

@Composable
fun ComprarIngressosScreen(navController: NavController) {
    val jogosDisponiveis = remember { jogosMock.filter { it.status != StatusIngresso.EM_BREVE } }
    val jogosEmBreve     = remember { jogosMock.filter { it.status == StatusIngresso.EM_BREVE } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoEscuro)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = "Próximos Jogos",
                fontSize   = 13.sp,
                fontFamily = BebasNeue,
                color      = TextoSecund
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp)
                .padding(bottom = 24.dp)
        ) {
            // Seção: Vendas Abertas
            SectionLabel(texto = "VENDAS ABERTAS")

            jogosDisponiveis.forEach { jogo ->
                CartaoJogo(
                    jogo       = jogo,
                    onComprar  = { /* navController.navigate("checkout/${jogo.id}") */ }
                )
                Spacer(Modifier.height(12.dp))
            }

            Spacer(Modifier.height(8.dp))

            // Seção: Em Breve
            SectionLabel(texto = "EM BREVE")

            jogosEmBreve.forEach { jogo ->
                CartaoJogo(
                    jogo           = jogo,
                    onComprar      = {},
                    onAvisar       = { /* lógica de notificação */ }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

// ────────────────────────────────────────────────
// Componentes
// ────────────────────────────────────────────────

@Composable
fun SectionLabel(texto: String) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text          = texto,
            fontSize      = 12.sp,
            fontFamily    = BebasNeue,
            color         = White,
            letterSpacing = 2.sp
        )
        Spacer(Modifier.width(8.dp))
        HorizontalDivider(
            modifier  = Modifier.weight(1f),
            color     = White.copy(alpha = 0.2f),
            thickness = 1.dp
        )
    }
}

@Composable
fun CartaoJogo(
    jogo     : JogoDisponivel,
    onComprar: () -> Unit,
    onAvisar : (() -> Unit)? = null
) {
    Card(
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = CardEscuro),
        border   = BorderStroke(1.dp, BordaCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Cabeçalho: competição + badge status
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp, top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text          = jogo.competicao,
                    fontSize      = 10.sp,
                    fontFamily    = BebasNeue,
                    color         = TextoSecund,
                    letterSpacing = 1.5.sp
                )
                BadgeStatus(status = jogo.status)
            }

            // Times
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text       = jogo.mandante,
                    fontSize   = 20.sp,
                    fontFamily = BebasNeue,
                    color      = VermelhoBotao,
                    letterSpacing = .5.sp
                )
                Text(
                    text       = "VS",
                    fontSize   = 14.sp,
                    fontFamily = BebasNeue,
                    color      = TextoSecund,
                    modifier   = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text       = jogo.visitante,
                    fontSize   = 20.sp,
                    fontFamily = BebasNeue,
                    color      = Color.White,
                    letterSpacing = .5.sp
                )
            }

            // Infos: data, hora, estádio
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                InfoItem(icon = Icons.Default.CalendarToday, texto = jogo.data)
                InfoItem(icon = Icons.Default.Schedule,      texto = jogo.hora)
                InfoItem(icon = Icons.Default.LocationOn,    texto = jogo.estadio)
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.06f))

            // Rodapé: preço + botão
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                when (jogo.status) {
                    StatusIngresso.DISPONIVEL -> {
                        Column {
                            Text("A partir de", fontSize = 10.sp, color = TextoSecund, fontFamily = BebasNeue)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (jogo.precoOriginal != null) {
                                    Text(
                                        text            = "R$ ${jogo.precoOriginal.toInt()}",
                                        fontSize        = 13.sp,
                                        fontFamily      = BebasNeue,
                                        color           = TextoSecund,
                                        textDecoration  = TextDecoration.LineThrough
                                    )
                                    Spacer(Modifier.width(4.dp))
                                }
                                Text(
                                    text       = "R$ ${jogo.precoMinimo?.toInt()}",
                                    fontSize   = 20.sp,
                                    fontFamily = BebasNeue,
                                    color      = Color.White
                                )
                                if (jogo.precoOriginal != null) {
                                    Spacer(Modifier.width(4.dp))
                                    val pct = ((1 - jogo.precoMinimo!! / jogo.precoOriginal) * 100).toInt()
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = VerdeConfirm.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text     = "-$pct%",
                                            fontSize = 10.sp,
                                            color    = VerdeConfirm,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Button(
                            onClick  = onComprar,
                            shape    = RoundedCornerShape(50.dp),
                            colors   = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                            modifier = Modifier.height(40.dp)
                        ) {
                            Text("Comprar", fontFamily = BebasNeue, color = Color.White, fontSize = 14.sp)
                        }
                    }

                    StatusIngresso.ESGOTADO -> {
                        Column {
                            Text("Ingressos", fontSize = 10.sp, color = TextoSecund, fontFamily = BebasNeue)
                            Text(
                                text       = "Esgotado",
                                fontSize   = 16.sp,
                                fontFamily = BebasNeue,
                                color      = VermelhoBotao
                            )
                        }
                        Button(
                            onClick  = {},
                            enabled  = false,
                            shape    = RoundedCornerShape(50.dp),
                            colors   = ButtonDefaults.buttonColors(
                                disabledContainerColor = Color(0xFF2A2A2A),
                                disabledContentColor   = TextoSecund
                            ),
                            modifier = Modifier.height(40.dp)
                        ) {
                            Text("Indisponível", fontFamily = BebasNeue, fontSize = 14.sp)
                        }
                    }

                    StatusIngresso.EM_BREVE -> {
                        Column {
                            Text("Vendas abrem em", fontSize = 10.sp, color = TextoSecund, fontFamily = BebasNeue)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = AmareloFundo, modifier = Modifier.size(14.dp))
                                Text(
                                    text       = jogo.dataAbertura ?: "",
                                    fontSize   = 16.sp,
                                    fontFamily = BebasNeue,
                                    color      = AmareloFundo
                                )
                            }
                        }
                        OutlinedButton(
                            onClick  = { onAvisar?.invoke() },
                            shape    = RoundedCornerShape(50.dp),
                            border   = BorderStroke(1.dp, AmareloFundo.copy(alpha = 0.4f)),
                            colors   = ButtonDefaults.outlinedButtonColors(contentColor = AmareloFundo),
                            modifier = Modifier.height(40.dp)
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = AmareloFundo, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Avisar-me", fontFamily = BebasNeue, fontSize = 13.sp, color = AmareloFundo)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeStatus(status: StatusIngresso) {
    val (texto, cor) = when (status) {
        StatusIngresso.DISPONIVEL -> "Disponível" to VerdeConfirm
        StatusIngresso.ESGOTADO   -> "Esgotado"   to VermelhoBotao
        StatusIngresso.EM_BREVE   -> "Em breve"   to AmareloFundo
    }
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = cor.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, cor.copy(alpha = 0.3f))
    ) {
        Text(
            text       = texto,
            fontSize   = 11.sp,
            fontFamily = BebasNeue,
            color      = cor,
            modifier   = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun InfoItem(
    icon : androidx.compose.ui.graphics.vector.ImageVector,
    texto: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = VermelhoBotao,
            modifier           = Modifier.size(12.dp)
        )
        Text(texto, fontSize = 11.sp, color = TextoSecund, fontFamily = BebasNeue)
    }
}