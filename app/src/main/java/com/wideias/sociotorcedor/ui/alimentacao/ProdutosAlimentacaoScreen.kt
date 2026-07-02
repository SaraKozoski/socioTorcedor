package com.wideias.sociotorcedor.ui.alimentacao

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.LocalFireDepartment
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
fun ProdutosAlimentacaoScreen(
    navController: NavController,
    carrinhoViewModel: CarrinhoViewModel,
    userViewModel: UserViewModel
) {
    val itens by carrinhoViewModel.itens.collectAsState()
    val quantidade = itens.size
    val total = carrinhoViewModel.precoTotal

    // 0 = Cardápio, 1 = Resgatar com Pontos
    var tabSelecionada by remember { mutableStateOf(0) }

    val categorias = remember {
        listOf("Todos") + produtosAlimentacaoMock.map { it.categoria }.distinct()
    }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }

    val produtosFiltrados = remember(categoriaSelecionada) {
        if (categoriaSelecionada == "Todos") produtosAlimentacaoMock
        else produtosAlimentacaoMock.filter { it.categoria == categoriaSelecionada }
    }

    val produtosPontos = remember {
        produtosAlimentacaoMock.filter { it.pontos != null && it.pontos > 0 }
    }

    Scaffold { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TabAlimentacao(
                        label = "CARDÁPIO",
                        selecionado = tabSelecionada == 0,
                        modifier = Modifier.weight(1f),
                        onClick = { tabSelecionada = 0 }
                    )
                    TabAlimentacao(
                        label = "RESGATAR",
                        selecionado = tabSelecionada == 1,
                        modifier = Modifier.weight(1f),
                        onClick = { tabSelecionada = 1 },
                        icone = {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = if (tabSelecionada == 1) Color.White else HomeColors.TextoCinza,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    )
                }

                when (tabSelecionada) {
                    0 -> {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
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

                    1 -> {
                        CardapioResgateScreen(
                            produtos = produtosPontos,
                            userViewModel = userViewModel,
                            onVerDetalhes = { produto ->
                                navController.navigate("descricao_produto_alimentacao/${produto.id}")
                            }
                        )
                    }
                }
            }

            if (quantidade > 0 && tabSelecionada == 0) {
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
private fun TabAlimentacao(
    label: String,
    selecionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icone: (@Composable RowScope.() -> Unit)? = null
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = if (selecionado) HomeColors.Cards1 else HomeColors.CardEscuro,
        border = if (!selecionado) BorderStroke(1.dp, HomeColors.CardEscuro) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icone?.invoke(this)
            Text(
                text = label,
                fontFamily = BebasNeue,
                fontSize = 14.sp,
                color = if (selecionado) Color.White else HomeColors.TextoCinza
            )
        }
    }
}


@Composable
fun CardapioResgateScreen(
    produtos: List<ProdutoAlimentacao>,
    userViewModel: UserViewModel,
    onVerDetalhes: (ProdutoAlimentacao) -> Unit
) {
    val pontosUsuario by userViewModel.pontos.collectAsState()
    var resgateAtivo by remember { mutableStateOf<ResgateInfo?>(null) }

    resgateAtivo?.let { info ->
        QrCodeResgateDialog(
            resgate = info,
            onDismiss = { resgateAtivo = null }
        )
    }

    val pontosMaximo = 250
    val valores = listOf(0, 50, 100, 150, 200, 250)
    val indexAtual = valores.indexOfLast { it <= pontosUsuario }.coerceAtLeast(0)
    val progresso = (pontosUsuario.toFloat() / pontosMaximo.toFloat()).coerceIn(0f, 1f)
    val isValorQuebrado = !valores.contains(pontosUsuario) && pontosUsuario > 0

    Column(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column {
                // Header: nome do nível + pontos atual
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(HomeColors.CardClaro)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SEUS PONTOS",
                        fontFamily = BebasNeue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = HomeColors.Preto
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = HomeColors.Cards1,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$pontosUsuario pts",
                            fontFamily = BebasNeue,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = HomeColors.Preto
                        )
                    }
                }

                // Barra de progresso
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(HomeColors.DetalhesCard1)
                        .padding(horizontal = 12.dp, vertical = 14.dp)
                ) {
                    // Tooltip do valor quebrado
                    if (isValorQuebrado) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth(progresso)) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .clip(RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "$pontosUsuario pts",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth().height(36.dp)) {
                        // Trilha de fundo
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color.Black.copy(alpha = 0.3f))
                        )
                        // Progresso preenchido
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progresso)
                                .height(36.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .border(
                                    width = 1.5.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(50.dp)
                                )
                        )
                        // Marcadores de nível
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            valores.forEachIndexed { index, valor ->
                                val isAtual = index == indexAtual && !isValorQuebrado
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (valor == 0) "0" else "$valor",
                                        color = if (index <= indexAtual) Color.White
                                                else Color.White.copy(alpha = 0.5f),
                                        fontWeight = if (isAtual) FontWeight.Bold
                                                     else FontWeight.Normal,
                                        fontFamily = BebasNeue,
                                        fontSize = if (isAtual) 15.sp else 13.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (produtos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Nenhum item disponível\npara resgate no momento.",
                    fontFamily = BebasNeue,
                    fontSize = 15.sp,
                    color = HomeColors.TextoCinza,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 12.dp, end = 12.dp, top = 8.dp, bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(produtos) { produto ->
                    val pontosProduto = produto.pontos ?: 0
                    val temPontos = pontosUsuario >= pontosProduto
                    CardResgate(
                        produto = produto,
                        pontosProduto = pontosProduto,
                        habilitado = temPontos,
                        onResgatar = {
                            if (temPontos) {
                                userViewModel.resgatarPontos(pontosProduto)
                                resgateAtivo = ResgateInfo(
                                    codigo = gerarCodigoResgate(),
                                    nomeProduto = produto.nome,
                                    pontos = pontosProduto
                                )
                            }
                        },
                        onVerDetalhes = { onVerDetalhes(produto) }
                    )
                }
            }
        }
    }
}

@Composable
fun CardResgate(
    produto: ProdutoAlimentacao,
    pontosProduto: Int,
    habilitado: Boolean,
    onResgatar: () -> Unit,
    onVerDetalhes: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerDetalhes() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = HomeColors.CardEscuro),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(97.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(HomeColors.DetalhesCard1),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = produto.imagemRes,
                    contentDescription = produto.nome,
                    modifier = Modifier
                        .size(87.dp)
                        .padding(4.dp),
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = if (habilitado) HomeColors.Cards1 else HomeColors.TextoCinza,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "$pontosProduto pts",
                    fontFamily = BebasNeue,
                    fontSize = 13.sp,
                    color = if (habilitado) HomeColors.Cards1 else HomeColors.TextoCinza
                )
            }

            Button(
                onClick = onResgatar,
                enabled = habilitado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HomeColors.Cards1,
                    disabledContainerColor = HomeColors.CardEscuro.copy(alpha = 0.5f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (habilitado) "RESGATAR" else "SEM PONTOS",
                    fontFamily = BebasNeue,
                    fontSize = 13.sp,
                    color = if (habilitado) Color.White else HomeColors.TextoCinza
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Componentes existentes (mantidos sem alteração)
// ─────────────────────────────────────────────────────────────────────────────

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