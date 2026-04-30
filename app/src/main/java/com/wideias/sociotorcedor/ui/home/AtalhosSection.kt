package com.wideias.sociotorcedor.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.theme.BebasNeue

@Composable
fun AtalhosSection(navController: NavController) {
    val context = LocalContext.current
    var atalhos by remember {
        mutableStateOf(AtalhoManager.getAtalhosOrdenados(context).take(6))
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val rota = destination.route ?: return@OnDestinationChangedListener
            AtalhoManager.registrarAcesso(context, rota)
            atalhos = AtalhoManager.getAtalhosOrdenados(context).take(6)
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "ACESSO RÁPIDO",
            fontFamily = BebasNeue,
            fontSize = 14.sp,
            color = HomeColors.TextoCinza,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            atalhos.take(3).forEach { atalho ->
                AtalhoCard(
                    atalho = atalho,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(atalho.rota) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            atalhos.drop(3).take(3).forEach { atalho ->
                AtalhoCard(
                    atalho = atalho,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(atalho.rota) }
                )
            }
            repeat(3 - atalhos.drop(3).size) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun AtalhoCard(
    atalho: Atalho,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(HomeColors.CardEscuro)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = atalho.icone,
            contentDescription = atalho.label,
            tint = HomeColors.Cards1,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = atalho.label.uppercase(),
            fontFamily = BebasNeue,
            fontSize = 11.sp,
            color = HomeColors.TextoBranco,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}
