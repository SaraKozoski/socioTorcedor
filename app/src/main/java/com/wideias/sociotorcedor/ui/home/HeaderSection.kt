package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.viewmodel.UserViewModel

@Composable
fun HeaderSection(
    navController: NavController,
    userViewModel: UserViewModel          // ← novo parâmetro
) {
    var menuAberto by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HomeColors.CardEscuro)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /* ── Menu hamburguer (esquerda) ── */
        Box {
            IconButton(onClick = { menuAberto = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = HomeColors.TextoBranco
                )
            }

            DropdownMenu(
                expanded = menuAberto,
                onDismissRequest = { menuAberto = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Planos de Sócio", fontFamily = BebasNeue, fontSize = 16.sp) },
                    onClick = { menuAberto = false; navController.navigate("planos") }
                )
                DropdownMenuItem(
                    text = { Text("Apostas", fontFamily = BebasNeue, fontSize = 16.sp) },
                    onClick = { menuAberto = false; navController.navigate("apostas") }
                )
            }
        }

        /* ── Logo central ── */
        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = "Logo do Clube",
            modifier = Modifier.size(48.dp)
        )

        /* ── Ícone de perfil (direita) ── */
        IconButton(
            onClick = {
                if (userViewModel.estaLogado) {   // ← ajuste para o campo real do seu ViewModel
                    navController.navigate("perfil")
                } else {
                    navController.navigate("login")
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = if (userViewModel.estaLogado) "Perfil" else "Login",
                tint = HomeColors.TextoBranco,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}