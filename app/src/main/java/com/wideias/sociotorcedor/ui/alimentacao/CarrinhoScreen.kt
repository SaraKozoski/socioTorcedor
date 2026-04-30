package com.wideias.sociotorcedor.ui.alimentacao

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wideias.sociotorcedor.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen(
    navController: NavController,
    carrinhoViewModel: CarrinhoViewModel,
    userViewModel: UserViewModel
) {
    val itens by carrinhoViewModel.itens.collectAsState()

    val itensAgrupados = itens.groupBy { it.produto.id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CARRINHO", fontFamily = BebasNeue, fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = HomeColors.CardEscuro)
            )
        },
        containerColor = HomeColors.Fundo,
        bottomBar = {
            if (itens.isNotEmpty()) {
                Surface(color = HomeColors.CardEscuro, shadowElevation = 12.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("TOTAL", fontFamily = BebasNeue, fontSize = 12.sp, color = HomeColors.TextoCinza)
                            Text(
                                "R$ ${String.format("%.2f", carrinhoViewModel.precoTotal)}",
                                fontFamily = BebasNeue, fontSize = 22.sp,
                                color = Color.White, fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = {  userViewModel.adicionarPontos(carrinhoViewModel.precoTotal)
                                carrinhoViewModel.limparCarrinho()
                                navController.popBackStack()},
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = HomeColors.Cards1),
                            modifier = Modifier.height(48.dp).widthIn(min = 160.dp)
                        ) {
                            Text("FINALIZAR PEDIDO", fontFamily = BebasNeue, fontSize = 14.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (itens.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Seu carrinho está vazio", fontFamily = BebasNeue, fontSize = 18.sp, color = HomeColors.TextoCinza)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(itensAgrupados.entries.toList(), key = { it.key }) { (produtoId, grupo) ->
                    val representante = grupo.first()
                    val quantidade = grupo.size
                    val precoUnitario = representante.precoTotal
                    val atingiuLimite = quantidade >= CarrinhoViewModel.LIMITE_POR_ITEM

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(HomeColors.CardEscuro)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = representante.produto.imagemRes,
                            contentDescription = representante.produto.nome,
                            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                representante.produto.nome.uppercase(),
                                fontFamily = BebasNeue, fontSize = 15.sp,
                                color = HomeColors.TextoBranco, fontWeight = FontWeight.Bold
                            )
                            if (representante.adicionaisSelecionados.isNotEmpty()) {
                                Text(
                                    representante.adicionaisSelecionados.joinToString(", ") { it.nome },
                                    fontFamily = BebasNeue, fontSize = 12.sp, color = HomeColors.TextoCinza
                                )
                            }
                            Text(
                                "R$ ${String.format("%.2f", precoUnitario * quantidade)}",
                                fontFamily = BebasNeue, fontSize = 16.sp,
                                color = HomeColors.Cards1, fontWeight = FontWeight.Bold
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(
                                onClick = { carrinhoViewModel.removerTodos(produtoId) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Remover", tint = Color(0xFFE53935), modifier = Modifier.size(18.dp))
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FilledTonalButton(
                                    onClick = { carrinhoViewModel.removerUm(produtoId) },
                                    modifier = Modifier.size(28.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = HomeColors.Fundo)
                                ) { Text("-", color = Color.White, fontSize = 16.sp) }

                                Text(
                                    "$quantidade",
                                    fontFamily = BebasNeue, fontSize = 16.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                FilledTonalButton(
                                    onClick = { carrinhoViewModel.adicionarItem(representante.copy(id = java.util.UUID.randomUUID().toString())) },
                                    enabled = !atingiuLimite,
                                    modifier = Modifier.size(28.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = HomeColors.Cards1,
                                        disabledContainerColor = HomeColors.TextoCinza
                                    )
                                ) { Text("+", color = Color.White, fontSize = 16.sp) }
                            }

                            if (atingiuLimite) {
                                Text("Máx. 15", fontFamily = BebasNeue, fontSize = 10.sp, color = Color(0xFFE53935))
                            }
                        }
                    }
                }
            }
        }
    }
}
