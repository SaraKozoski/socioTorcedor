package com.wideias.sociotorcedor.ui.alimentacao

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProdutosAlimentacaoScreen(navController: NavController, carrinhoViewModel: CarrinhoViewModel, userViewModel: UserViewModel) {
    val itens by carrinhoViewModel.itens.collectAsState()
    val quantidade = itens.size
    val total = carrinhoViewModel.precoTotal

    val categorias = remember {
        listOf("Todos") + produtosAlimentacaoMock.map { it.categoria }.distinct()
    }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }

    val produtosFiltrados = remember(categoriaSelecionada) {
        if (categoriaSelecionada == "Todos") produtosAlimentacaoMock
        else produtosAlimentacaoMock.filter { it.categoria == categoriaSelecionada }
    }

    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categorias) { categoria ->
                        val selecionado = categoria == categoriaSelecionada
                        Surface(
                            onClick = { categoriaSelecionada = categoria },
                            shape = RoundedCornerShape(20.dp),
                            color = if (selecionado) HomeColors.Cards1 else HomeColors.CardEscuro,
                            border = if (!selecionado) BorderStroke(1.dp, HomeColors.CardEscuro) else null
                        ) {
                            Text(
                                text = categoria.uppercase(),
                                fontFamily = BebasNeue,
                                fontSize = 14.sp,
                                color = if (selecionado) Color.White else HomeColors.TextoCinza,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        end = 12.dp,
                        top = 4.dp,
                        bottom = if (quantidade > 0) 90.dp else 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(produtosFiltrados) { produto ->
                        CardProduto(
                            produto = produto,
                            onAdicionar = {
                                carrinhoViewModel.adicionarItem(
                                    ItemCarrinho(
                                        id = java.util.UUID.randomUUID().toString(),
                                        produto = produto,
                                        ingredientesInclusos = produto.ingredientes.filter { it.incluso },
                                        adicionaisSelecionados = emptyList(),
                                        observacao = "",
                                        precoTotal = produto.preco
                                    )
                                )
                            },
                            onVerDetalhes = {
                                navController.navigate("descricao_produto_alimentacao/${produto.id}")
                            }
                        )
                    }
                }
            }

            if (quantidade > 0) {
                CartFooter(
                    quantidade = quantidade,
                    total = total,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = { navController.navigate("carrinho") }
                )
            }
        }
    }
}

@Composable
fun CardProduto(
    produto: ProdutoAlimentacao,
    onAdicionar: () -> Unit,
    onVerDetalhes: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onVerDetalhes() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = HomeColors.CardEscuro),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(97.dp).clip(RoundedCornerShape(10.dp)).background(HomeColors.DetalhesCard1),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = produto.imagemRes,
                    contentDescription = produto.nome,
                    modifier = Modifier.size(87.dp).padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = produto.nome,
                fontFamily = BebasNeue,
                fontSize = 15.sp,
                color = HomeColors.TextoBranco,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "R$ ${String.format("%.2f", produto.preco)}",
                fontFamily = BebasNeue,
                fontSize = 13.sp,
                color = HomeColors.TextoCinza,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Button(
                onClick = onAdicionar,
                modifier = Modifier.fillMaxWidth().height(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HomeColors.Cards1),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "ADICIONAR", fontFamily = BebasNeue, fontSize = 13.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun CartFooter(
    quantidade: Int,
    total: Double,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        color = HomeColors.Cards1,
        shadowElevation = 12.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(24.dp).clip(RoundedCornerShape(50.dp)).background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$quantidade", fontFamily = BebasNeue, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Item${if (quantidade > 1) "s" else ""} • R$ ${String.format("%.2f", total)}",
                    fontFamily = BebasNeue, fontSize = 16.sp, color = Color.White
                )
            }
            Text(text = "CARRINHO  →", fontFamily = BebasNeue, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }
    }
}
