package com.wideias.sociotorcedor.ui.apostas

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
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

// Substitua pela URL real do parceiro de apostas
private const val URL_APOSTAS = "https://www.parceiroapostas.com.br"

private val CardEscuro    = Color(0xFF1E1E1E)
private val TextoSecund   = Color(0xFFAAAAAA)
private val VermelhoBotao = Color(0xFFE53935)
private val AmareloFundo  = Color(0xFFFFC107)
private val VerdeConfirm  = Color(0xFF4CAF50)
private val FundoBanner1  = Color(0xFF1A0000)
private val FundoBanner2  = Color(0xFF4A0000)
private val FundoBanner3  = Color(0xFF7A1010)

@Composable
fun ApostasScreen(navController: androidx.navigation.NavController? = null) {
    val context = LocalContext.current

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
                text       = "Parceiro Oficial do Clube",
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
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Banner Principal
            BannerAposta(onApostarClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_APOSTAS))
                context.startActivity(intent)
            })

            Text(
                text      = "* Jogue com responsabilidade. Proibido para menores de 18 anos.",
                fontSize  = 10.sp,
                color     = Color.White.copy(alpha = 0.3f),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )

            // Odds próximo jogo
            CardOdds()

            // Benefícios
            CardBeneficios()
        }
    }
}

@Composable
fun BannerAposta(onApostarClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(FundoBanner1, FundoBanner2, FundoBanner3)
                )
            )
    ) {
        Column(modifier = Modifier.padding(22.dp)) {

            // Badge parceiro
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = AmareloFundo.copy(alpha = 0.15f),
                modifier = Modifier
                    .border(1.dp, AmareloFundo.copy(alpha = 0.4f), RoundedCornerShape(50.dp))
            ) {
                Text(
                    text     = "PARCEIRO OFICIAL",
                    fontSize = 11.sp,
                    fontFamily = BebasNeue,
                    color    = AmareloFundo,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 3.dp)
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = "APOSTE",
                fontSize   = 40.sp,
                fontFamily = BebasNeue,
                color      = Color.White,
                lineHeight = 36.sp,
                letterSpacing = 2.sp
            )
            Text(
                text = "AGORA",
                fontSize   = 40.sp,
                fontFamily = BebasNeue,
                color      = AmareloFundo,
                lineHeight = 36.sp,
                letterSpacing = 2.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text       = "As melhores odds para os jogos do seu clube. Bônus exclusivo para sócios-torcedores.",
                fontSize   = 12.sp,
                color      = Color.White.copy(alpha = 0.65f),
                lineHeight = 18.sp
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick  = onApostarClick,
                shape    = RoundedCornerShape(50.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = AmareloFundo),
                modifier = Modifier.height(46.dp)
            ) {
                Icon(
                    imageVector        = Icons.Default.OpenInBrowser,
                    contentDescription = null,
                    tint               = Color(0xFF1A0000),
                    modifier           = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = "APOSTE AQUI",
                    fontFamily = BebasNeue,
                    fontSize   = 16.sp,
                    letterSpacing = 1.5.sp,
                    color      = Color(0xFF1A0000)
                )
            }
        }
    }
}

@Composable
fun CardOdds() {
    Card(
        shape  = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardEscuro),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text       = "ODDS DO PRÓXIMO JOGO",
                fontSize   = 13.sp,
                fontFamily = BebasNeue,
                color      = TextoSecund,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text       = "Atlético × Flamengo  ·  07/06",
                fontSize   = 11.sp,
                fontFamily = BebasNeue,
                color      = TextoSecund
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OddItem(label = "Casa",   valor = "2.10", destaque = true,  modifier = Modifier.weight(1f))
                OddItem(label = "Empate", valor = "3.40", destaque = false, modifier = Modifier.weight(1f))
                OddItem(label = "Fora",   valor = "3.20", destaque = false, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun OddItem(label: String, valor: String, destaque: Boolean, modifier: Modifier = Modifier) {
    Surface(
        shape    = RoundedCornerShape(10.dp),
        color    = Color(0xFF141414),
        border   = BorderStroke(1.dp, Color(0xFF2A2A2A)),
        modifier = modifier
    ) {
        Column(
            modifier            = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, fontSize = 10.sp, color = TextoSecund, fontFamily = BebasNeue)
            Spacer(Modifier.height(4.dp))
            Text(
                text       = valor,
                fontSize   = 22.sp,
                fontFamily = BebasNeue,
                color      = if (destaque) AmareloFundo else Color.White
            )
        }
    }
}

@Composable
fun CardBeneficios() {
    Card(
        shape    = RoundedCornerShape(14.dp),
        colors   = CardDefaults.cardColors(containerColor = CardEscuro),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text       = "POR QUE APOSTAR AQUI?",
                fontSize   = 13.sp,
                fontFamily = BebasNeue,
                color      = TextoSecund,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(14.dp))

            BeneficioItem(
                icon  = Icons.Default.Star,
                cor   = AmareloFundo,
                titulo = "Bônus de Boas-vindas",
                desc  = "Até R$ 200 em créditos no primeiro depósito"
            )
            Spacer(Modifier.height(12.dp))
            BeneficioItem(
                icon  = Icons.Default.Shield,
                cor   = VerdeConfirm,
                titulo = "Site Seguro e Licenciado",
                desc  = "Operação regulamentada no Brasil"
            )
            Spacer(Modifier.height(12.dp))
            BeneficioItem(
                icon  = Icons.Default.Star,
                cor   = VermelhoBotao,
                titulo = "Odds Exclusivas para Sócios",
                desc  = "Melhores cotas nos jogos do seu clube"
            )
        }
    }
}

@Composable
fun BeneficioItem(
    icon  : androidx.compose.ui.graphics.vector.ImageVector,
    cor   : Color,
    titulo: String,
    desc  : String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = cor,
            modifier           = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Text(titulo, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(desc,   fontSize = 11.sp, color = TextoSecund)
        }
    }
}