package com.wideias.sociotorcedor.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wideias.sociotorcedor.ui.login.LoginScreen

sealed class Tela(val rota: String) {
    object Login : Tela("login")
    object Home : Tela("home")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Tela.Login.rota) {
        composable(Tela.Login.rota) {
            LoginScreen(
                onLoginSucesso = {
                    navController.navigate(Tela.Home.rota) {
                        popUpTo(Tela.Login.rota) { inclusive = true }
                    }
                },
                onCadastroClick = { }
            )
        }
        composable(Tela.Home.rota) {
            Text("Home - Em construção 🚧")
        }
    }
}