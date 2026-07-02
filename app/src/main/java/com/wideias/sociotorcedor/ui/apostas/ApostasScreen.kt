package com.wideias.sociotorcedor.ui.apostas

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.ui.theme.FundoEscuro
import com.wideias.sociotorcedor.ui.theme.AppColors

private const val URL_APOSTAS = "https://www.parceiroapostas.com.br"

private val Fundo         get() = AppColors.apostasFundo
private val SuperficieA   get() = AppColors.apostasSurfaceA
private val SuperficieB   get() = AppColors.apostasSurfaceB
private val Borda         get() = AppColors.apostasBorder
private val Dourado       get() = AppColors.apostasGold
private val DouradoClaro  get() = AppColors.apostasGoldLight
private val TextoPrimario get() = AppColors.apostasTextPrimary
private val TextoSecund   get() = AppColors.apostasTextSecondary
private val VerdeAcento   get() = AppColors.apostasGreen

@Composable
fun ApostasScreen(navController: androidx.navigation.NavController? = null) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Hero ──────────────────────────────────────────────────
        HeroBanner(onApostarClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_APOSTAS)))
        })

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Como funciona ──────────────────────────────────────
            Spacer(Modifier.height(4.dp))
            SectionLabel("COMO APOSTAR")
            PassosCard()

            // ── Odds ───────────────────────────────────────────────
            SectionLabel("ODDS AO VIVO")
            OddsCard()

            // ── Vantagens ─────────────────────────────────────────
            SectionLabel("VANTAGENS DO SÓCIO")
            VantagensCard()

            // ── CTA secundário ────────────────────────────────────
            CtaSecundario(onApostarClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_APOSTAS)))
            })

            // ── Aviso legal ───────────────────────────────────────
            Text(
                text = "Jogue com responsabilidade. Proibido para menores de 18 anos. Se o jogo deixar de ser divertido, busque ajuda.",
                fontSize  = 10.sp,
                color     = TextoSecund.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

// ── Hero ──────────────────────────────────────────────────────────────────────

@Composable
fun HeroBanner(onApostarClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(AppColors.apostasGradienteTopo, Fundo)
                )   
            )
            .padding(horizontal = 20.dp, vertical = 36.dp)
    ) {
        Column {
            // Pílula de destaque
            Surface(
                shape  = RoundedCornerShape(50),
                color  = Dourado.copy(alpha = 0.15f),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text     = "⚡  EXCLUSIVO PARA SÓCIOS",
                    color    = Dourado,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                )
            }

            Text(
                text       = "Aposte nos\njogos do seu\nclube",
                fontFamily = BebasNeue,
                fontSize   = 48.sp,
                lineHeight = 50.sp,
                color      = TextoPrimario,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text      = "Odds melhores que as casas comuns, bônus de boas-vindas e saque rápido.",
                fontSize  = 13.sp,
                color     = TextoSecund,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick  = onApostarClick,
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = Dourado),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text       = "CRIAR CONTA E APOSTAR",
                    fontFamily = BebasNeue,
                    fontSize   = 18.sp,
                    letterSpacing = 1.sp,
                    color      = Color(0xFF0D0D0D)
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector        = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint               = Color(0xFF0D0D0D),
                    modifier           = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            // Garantia rápida
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment     = Alignment.CenterVertically,
                modifier              = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = VerdeAcento, modifier = Modifier.size(13.dp))
                Spacer(Modifier.width(5.dp))
                Text("Site seguro e licenciado no Brasil", fontSize = 11.sp, color = TextoSecund)
            }
        }
    }

    // Divisor com gradiente
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(Color.Transparent, Dourado.copy(alpha = 0.4f), Color.Transparent)
                )
            )
    )
}

// ── Label de seção ────────────────────────────────────────────────────────────

@Composable
fun SectionLabel(texto: String) {
    Text(
        text          = texto,
        fontSize      = 10.sp,
        fontFamily    = BebasNeue,
        color         = Dourado,
        letterSpacing = 2.sp,
        modifier      = Modifier.padding(top = 8.dp, bottom = 2.dp)
    )
}

// ── Como funciona ─────────────────────────────────────────────────────────────

@Composable
fun PassosCard() {
    Surface(
        shape  = RoundedCornerShape(16.dp),
        color  = SuperficieA,
        border = BorderStroke(1.dp, Borda),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PassoItem(numero = "1", titulo = "Crie sua conta", desc = "Clique em 'Criar conta' e preencha seus dados em menos de 2 minutos.")
            HorizontalDivider(color = Borda, thickness = 0.5.dp)
            PassoItem(numero = "2", titulo = "Faça seu primeiro depósito", desc = "A partir de R$ 20, via Pix instantâneo.")
            HorizontalDivider(color = Borda, thickness = 0.5.dp)
            PassoItem(numero = "3", titulo = "Escolha o jogo e aposte", desc = "Selecione o mercado, informe o valor e confirme.")
        }
    }
}

@Composable
fun PassoItem(numero: String, titulo: String, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        // Número destacado
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Dourado.copy(alpha = 0.12f))
        ) {
            Text(
                text       = numero,
                fontFamily = BebasNeue,
                fontSize   = 18.sp,
                color      = Dourado
            )
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(titulo, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextoPrimario)
            Spacer(Modifier.height(3.dp))
            Text(desc,   fontSize = 12.sp, color = TextoSecund, lineHeight = 18.sp)
        }
    }
}

// ── Odds ──────────────────────────────────────────────────────────────────────

@Composable
fun OddsCard() {
    Surface(
        shape  = RoundedCornerShape(16.dp),
        color  = SuperficieA,
        border = BorderStroke(1.dp, Borda),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Column {
                    Text("Atlético × Flamengo", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextoPrimario)
                    Text("Brasileirão · 07/06 · 21h", fontSize = 11.sp, color = TextoSecund)
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = VerdeAcento.copy(alpha = 0.15f)
                ) {
                    Text(
                        "AO VIVO",
                        fontSize = 9.sp,
                        color    = VerdeAcento,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OddChip(label = "Atlético vence", valor = "2.10", destaque = true,  modifier = Modifier.weight(1f))
                OddChip(label = "Empate",         valor = "3.40", destaque = false, modifier = Modifier.weight(1f))
                OddChip(label = "Flamengo vence", valor = "3.20", destaque = false, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Toque em uma odd para ir direto a esse mercado.",
                fontSize = 10.sp,
                color    = TextoSecund,
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun OddChip(label: String, valor: String, destaque: Boolean, modifier: Modifier = Modifier) {
    Surface(
        shape  = RoundedCornerShape(12.dp),
        color  = if (destaque) Dourado.copy(alpha = 0.1f) else SuperficieB,
        border = BorderStroke(1.dp, if (destaque) Dourado.copy(alpha = 0.5f) else Borda),
        modifier = modifier
    ) {
        Column(
            modifier            = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                label,
                fontSize  = 9.sp,
                color     = TextoSecund,
                textAlign = TextAlign.Center,
                lineHeight = 13.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                valor,
                fontFamily = BebasNeue,
                fontSize   = 26.sp,
                color      = if (destaque) Dourado else TextoPrimario
            )
        }
    }
}

// ── Vantagens ─────────────────────────────────────────────────────────────────

@Composable
fun VantagensCard() {
    Surface(
        shape  = RoundedCornerShape(16.dp),
        color  = SuperficieA,
        border = BorderStroke(1.dp, Borda),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            VantagemItem(
                emoji  = "🎁",
                titulo = "R$ 200 de bônus no primeiro depósito",
                desc   = "Válido para novos usuários. Crédito liberado após a primeira aposta."
            )
            HorizontalDivider(color = Borda, thickness = 0.5.dp)
            VantagemItem(
                emoji  = "⚡",
                titulo = "Saque via Pix em até 10 minutos",
                desc   = "Sem burocracia, disponível 24 h por dia."
            )
            HorizontalDivider(color = Borda, thickness = 0.5.dp)
            VantagemItem(
                emoji  = "🏆",
                titulo = "Odds exclusivas para sócios",
                desc   = "Cotas até 15 % melhores nos jogos do seu clube."
            )
        }
    }
}

@Composable
fun VantagemItem(emoji: String, titulo: String, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(emoji, fontSize = 22.sp)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(titulo, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextoPrimario)
            Spacer(Modifier.height(3.dp))
            Text(desc,   fontSize = 12.sp, color = TextoSecund, lineHeight = 18.sp)
        }
    }
}

// ── CTA secundário ────────────────────────────────────────────────────────────

@Composable
fun CtaSecundario(onApostarClick: () -> Unit) {
    OutlinedButton(
        onClick  = onApostarClick,
        shape    = RoundedCornerShape(12.dp),
        border   = BorderStroke(1.dp, Dourado.copy(alpha = 0.6f)),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            text       = "VER TODOS OS MERCADOS",
            fontFamily = BebasNeue,
            fontSize   = 16.sp,
            letterSpacing = 1.sp,
            color      = Dourado
        )
    }
}