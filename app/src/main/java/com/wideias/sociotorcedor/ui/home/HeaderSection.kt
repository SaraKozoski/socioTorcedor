package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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

@Composable
fun HeaderSection(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HomeColors.CardEscuro)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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
                    text = {
                        Text(
                            text = "Planos de Sócio",
                            fontFamily = BebasNeue,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        menuAberto = false
                        navController.navigate("planos")
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Carrinho",
                            fontFamily = BebasNeue,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    },
                    onClick = {
                        menuAberto = false
                        navController.navigate("carrinho")
                    }
                )
                 DropdownMenuItem(
                    text = {
                        Text(
                            text = "LOGIN",
                            fontFamily = BebasNeue,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        menuAberto = false
                        navController.navigate("login")
                    }
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = "Logo do Clube",
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.size(48.dp))
    }
}