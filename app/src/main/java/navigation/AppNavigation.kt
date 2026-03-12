package com.wideias.sociotorcedor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wideias.sociotorcedor.ui.home.HomeScreen

sealed class Tela(val rota: String) {
    object Home : Tela("home")
    object Lanchonete : Tela("lanchonete")
    object Credito : Tela("credito")
    object Ingresso : Tela("ingresso")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Tela.Home.rota
    ) {
        composable(Tela.Home.rota) {
            HomeScreen(navController = navController)
        }

        composable(Tela.Lanchonete.rota) {
            // LanchoneteScreen() - implementar depois
        }

        composable(Tela.Credito.rota) {
            // CreditoScreen() - implementar depois
        }

        composable(Tela.Ingresso.rota) {
            // IngressoScreen() - implementar depois
        }
    }
}