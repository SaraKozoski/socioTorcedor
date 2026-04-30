package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wideias.sociotorcedor.viewmodel.UserViewModel
import androidx.compose.runtime.getValue  

@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel) {
    val usuario by userViewModel.usuario.collectAsState()
    val pontos by userViewModel.pontos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.Fundo)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        BetCard(
            nomeUsuario = usuario?.nome ?: "N/A",
            pontosAtual = pontos,
            pontosMaximo = 250
        )

        Spacer(modifier = Modifier.height(20.dp))
        ResultsSection()
        Spacer(modifier = Modifier.height(20.dp))

        if (usuario != null) {
            AtalhosSection(navController = navController)
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (usuario == null || !userViewModel.temPlano) {
            MembershipSection(navController = navController)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}