package com.wideias.sociotorcedor.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wideias.sociotorcedor.ui.home.HeaderSection
import com.wideias.sociotorcedor.ui.home.HomeScreen
import com.wideias.sociotorcedor.ui.planos.PlanoDetalheScreen
import com.wideias.sociotorcedor.ui.planos.PlanosScreen
import com.wideias.sociotorcedor.ui.time.TimeScreen
import com.wideias.sociotorcedor.ui.time.JogadorDetalheScreen


sealed class Tela(val rota: String) {
    object Home         : Tela("home")
    object Lanchonete   : Tela("lanchonete")
    object Credito      : Tela("credito")
    object Ingresso     : Tela("ingresso")
    object Planos       : Tela("planos")
    object PlanoDetalhe : Tela("plano_detalhe/{tipoPlano}") {
        fun comTipo(tipo: String) = "plano_detalhe/$tipo"
    }
    object Time : Tela("time")
}

private val telasComNavegacao = listOf(
    Tela.Home.rota,
    Tela.Lanchonete.rota,
    Tela.Credito.rota,
    Tela.Ingresso.rota,
    Tela.Planos.rota,
    Tela.Time.rota,
    "plano_detalhe"
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    val mostrarNavegacao = telasComNavegacao.any { rota ->
        rotaAtual?.startsWith(rota) == true
    }

    if (mostrarNavegacao) {
        Scaffold(
            topBar = { HeaderSection(navController = navController) },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            NavegacaoInterna(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    } else {
        NavegacaoInterna(navController = navController, modifier = Modifier)
    }
}

@Composable
fun NavegacaoInterna(navController: NavController, modifier: Modifier) {
    NavHost(
        navController = navController as androidx.navigation.NavHostController,
        startDestination = Tela.Home.rota,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(220)) },
        exitTransition = { fadeOut(animationSpec = tween(220)) },
        popEnterTransition = { fadeIn(animationSpec = tween(220)) },
        popExitTransition = { fadeOut(animationSpec = tween(220)) }
    ) {
        composable(Tela.Home.rota) {
            HomeScreen(navController = navController)
        }

        composable(Tela.Lanchonete.rota) { }

        composable(Tela.Credito.rota) { }

        composable(Tela.Ingresso.rota) {
            PlanosScreen(navController = navController)
        }

        composable(Tela.Planos.rota) {
            PlanosScreen(navController = navController)
        }

        composable(
            route = Tela.PlanoDetalhe.rota,

            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(320, easing = FastOutSlowInEasing)
                )
            },

            exitTransition = {
                fadeOut(animationSpec = tween(1))
            },

            popEnterTransition = {
                fadeIn(animationSpec = tween(1))
            },

            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                )
            }
        ) { backStackEntry ->
            val tipoPlano = backStackEntry.arguments?.getString("tipoPlano") ?: "RED"
            PlanoDetalheScreen(tipoPlano = tipoPlano, navController = navController)
        }

        composable(Tela.Time.rota) {
            TimeScreen(navController = navController)
        }
        composable("jogador/{atletaId}") { backStackEntry ->
    val id = backStackEntry.arguments?.getString("atletaId")?.toInt() ?: 0
    JogadorDetalheScreen(atletaId = id, navController = navController)
}
    }
}