package com.wideias.sociotorcedor.ui.time

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

enum class AbaTime { CLASSIFICACAO, ULTIMOS_JOGOS, ELENCO }

@Composable
fun TimeScreen(
    navController: NavController,
    viewModel: TimeViewModel = viewModel()
) {
    var abaSelecionada by remember { mutableStateOf(AbaTime.ULTIMOS_JOGOS) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.Fundo)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TabItem(
                texto = "CLASSIFICAÇÃO",
                selecionado = abaSelecionada == AbaTime.CLASSIFICACAO,
                onClick = { abaSelecionada = AbaTime.CLASSIFICACAO },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                texto = "ÚLTIMOS JOGOS",
                selecionado = abaSelecionada == AbaTime.ULTIMOS_JOGOS,
                onClick = { abaSelecionada = AbaTime.ULTIMOS_JOGOS },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                texto = "ELENCO",
                selecionado = abaSelecionada == AbaTime.ELENCO,
                onClick = { abaSelecionada = AbaTime.ELENCO },
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1C0D0D))
        ) {
            when (abaSelecionada) {
                AbaTime.CLASSIFICACAO -> ClassificacaoScreen(viewModel = viewModel)
                AbaTime.ULTIMOS_JOGOS -> UltimosJogosScreen(viewModel = viewModel)
                AbaTime.ELENCO        -> ElencoScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TabItem(
    texto: String,
    selecionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selecionado) HomeColors.Cards1 else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (selecionado) HomeColors.Cards1 else Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(50.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontFamily = BebasNeue,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}