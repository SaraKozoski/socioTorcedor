package com.wideias.sociotorcedor.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue

sealed class ItemNavegacao(val rota: String, val label: String) {
    object Home : ItemNavegacao("home", "Home")
    object Lanchonete : ItemNavegacao("lanchonete", "Lanchonete")
    object Logo : ItemNavegacao("logo", "")
    object Credito : ItemNavegacao("credito", "Crédito")
    object Ingresso : ItemNavegacao("ingresso", "Ingresso")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    NavigationBar(
        containerColor = HomeColors.BottomBar,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = rotaAtual == ItemNavegacao.Home.rota,
            onClick = { navController.navigate(ItemNavegacao.Home.rota) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontFamily = BebasNeue) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HomeColors.Cards1,
                selectedTextColor = HomeColors.Cards1,
                unselectedIconColor = HomeColors.TextoCinza,
                unselectedTextColor = HomeColors.TextoCinza,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = rotaAtual == ItemNavegacao.Lanchonete.rota,
            onClick = { navController.navigate(ItemNavegacao.Lanchonete.rota) },
            icon = { Icon(Icons.Default.Restaurant, contentDescription = "Lanchonete") },
            label = { Text("Lanchonete", fontFamily = BebasNeue) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HomeColors.Cards1,
                selectedTextColor = HomeColors.Cards1,
                unselectedIconColor = HomeColors.TextoCinza,
                unselectedTextColor = HomeColors.TextoCinza,
                indicatorColor = Color.Transparent
            )
        )

        // Logo central
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(HomeColors.Cards1),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_clube),
                        contentDescription = "Logo",
                        modifier = Modifier.size(36.dp)
                    )
                }
            },
            label = { Text("") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = rotaAtual == ItemNavegacao.Credito.rota,
            onClick = { navController.navigate(ItemNavegacao.Credito.rota) },
            icon = { Icon(Icons.Default.CreditCard, contentDescription = "Crédito") },
            label = { Text("Crédito", fontFamily = BebasNeue) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HomeColors.Cards1,
                selectedTextColor = HomeColors.Cards1,
                unselectedIconColor = HomeColors.TextoCinza,
                unselectedTextColor = HomeColors.TextoCinza,
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = rotaAtual == ItemNavegacao.Ingresso.rota,
            onClick = { navController.navigate(ItemNavegacao.Ingresso.rota) },
            icon = { Icon(Icons.Default.ConfirmationNumber, contentDescription = "Ingresso") },
            label = { Text("Ingresso", fontFamily = BebasNeue) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = HomeColors.Cards1,
                selectedTextColor = HomeColors.Cards1,
                unselectedIconColor = HomeColors.TextoCinza,
                unselectedTextColor = HomeColors.TextoCinza,
                indicatorColor = Color.Transparent
            )
        )
    }
}