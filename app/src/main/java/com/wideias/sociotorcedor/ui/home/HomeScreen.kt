package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wideias.sociotorcedor.navigation.BottomNavigationBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = HomeColors.Fundo
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            BetCard(
                nomeUsuario = "N/A",
                saldoAtual = "R\$0,00",
                pontosAtual = 0,     
                pontosMaximo = 250,
                onAdicionarCredito = { /* navegar para tela de crédito */ }
            )
            Spacer(modifier = Modifier.height(24.dp))
            ResultsSection()
            Spacer(modifier = Modifier.height(24.dp))
            MembershipSection()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}