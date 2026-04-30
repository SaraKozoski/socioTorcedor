package com.wideias.sociotorcedor.ui.alimentacao

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.launch
import androidx.compose.material.icons.automirrored.filled.ArrowBack



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescricaoProdutoAlimentacaoScreen(
    produtoId: String,
    navController: NavController,
    carrinhoViewModel: CarrinhoViewModel = viewModel()
) {
    val produto = remember(produtoId) {
        produtosAlimentacaoMock.find { it.id == produtoId }
            ?: produtosAlimentacaoMock.first()
    }

    var ingredientes by remember { mutableStateOf(produto.ingredientes) }
    var adicionais   by remember { mutableStateOf(produto.adicionais) }
    var observacao   by remember { mutableStateOf("") }

    val precoAdicionais = adicionais.filter { it.selecionado }.sumOf { it.preco }
    val precoTotal      = produto.preco + precoAdicionais
    val snackbarHostState = remember { SnackbarHostState()}
    val scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DETALHES DO PRODUTO",
                        fontFamily = BebasNeue,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HomeColors.CardEscuro
                )
            )
        },
        containerColor = HomeColors.Fundo,
        bottomBar = {
            Surface(
                color = HomeColors.CardEscuro,
                shadowElevation = 12.dp,
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TOTAL",
                            fontFamily = BebasNeue,
                            fontSize = 12.sp,
                            color = HomeColors.TextoCinza
                        )
                        Text(
                            text = "R$ ${String.format("%.2f", precoTotal)}",
                            fontFamily = BebasNeue,
                            fontSize = 22.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            val item = ItemCarrinho(
                                id = java.util.UUID.randomUUID().toString(),
                                produto = produto,
                                ingredientesInclusos = ingredientes.filter { it.incluso },
                                adicionaisSelecionados = adicionais.filter { it.selecionado },
                                observacao = observacao,
                                precoTotal = precoTotal
                            )
                            val sucesso = carrinhoViewModel.adicionarItem(item)
                            scope.launch {
                                if (sucesso) {
                                    navController.popBackStack()
                                } else {
                                    snackbarHostState.showSnackbar("Limite de ${CarrinhoViewModel.LIMITE_POR_ITEM} unidades por produto atingido.")
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HomeColors.Cards1),
                        modifier = Modifier.height(48.dp).widthIn(min = 180.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ADICIONAR AO CARRINHO",
                            fontFamily = BebasNeue,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp).background(HomeColors.DetalhesCard1),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = produto.imagemRes,
                    contentDescription = produto.nome,
                    modifier = Modifier.size(100.dp).padding(4.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                Text(
                    text = produto.nome.uppercase(),
                    fontFamily = BebasNeue,
                    fontSize = 26.sp,
                    color = HomeColors.TextoBranco,
                    fontWeight = FontWeight.Bold
                )
                if (produto.descricao.isNotBlank()) {
                    Text(
                        text = produto.descricao,
                        fontFamily = BebasNeue,
                        fontSize = 14.sp,
                        color = HomeColors.TextoCinza,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Text(
                    text = "R$ ${String.format("%.2f", produto.preco)}",
                    fontFamily = BebasNeue,
                    fontSize = 18.sp,
                    color = HomeColors.Cards1,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            HorizontalDivider(color = HomeColors.CardEscuro, thickness = 1.dp)

            if (ingredientes.isNotEmpty()) {
                HorizontalDivider(color = HomeColors.CardEscuro, thickness = 1.dp)
                SecaoTitle(titulo = "INGREDIENTES")
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ingredientes.forEach { item ->
                        IngredienteRow(
                            ingrediente = item,
                            onToggle = {
                                ingredientes = ingredientes.map {
                                    if (it.id == item.id) it.copy(incluso = !it.incluso) else it
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (adicionais.isNotEmpty()) {
                HorizontalDivider(color = HomeColors.CardEscuro, thickness = 1.dp)
                SecaoTitle(titulo = "ADICIONAIS")
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    adicionais.forEach { item ->
                        AdicionalRow(
                            adicional = item,
                            onToggle = {
                                adicionais = adicionais.map {
                                    if (it.id == item.id) it.copy(selecionado = !it.selecionado) else it
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            HorizontalDivider(color = HomeColors.CardEscuro, thickness = 1.dp)

            SecaoTitle(titulo = "OBSERVAÇÕES")

            OutlinedTextField(
                value = observacao,
                onValueChange = { observacao = it },
                placeholder = {
                    Text(
                        text = "Ex: tirar cebola, ponto da carne...",
                        fontFamily = BebasNeue,
                        fontSize = 13.sp,
                        color = HomeColors.TextoCinza
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 24.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = HomeColors.Cards1,
                    unfocusedBorderColor = HomeColors.CardEscuro,
                    cursorColor = HomeColors.Cards1,
                    focusedContainerColor = HomeColors.CardEscuro,
                    unfocusedContainerColor = HomeColors.CardEscuro
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = BebasNeue,
                    fontSize = 14.sp
                ),
                minLines = 2,
                maxLines = 4
            )
        }
    }
}


@Composable
private fun SecaoTitle(titulo: String) {
    Text(
        text = titulo,
        fontFamily = BebasNeue,
        fontSize = 17.sp,
        color = HomeColors.TextoBranco,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun IngredienteRow(
    ingrediente: IngredienteAlimentacao,
    onToggle: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (ingrediente.incluso) HomeColors.CardEscuro else HomeColors.Cards1.copy(alpha = 0.15f),
        label = "ingrediente_bg"
    )

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clip(RoundedCornerShape(8.dp)).background(bgColor).clickable { onToggle() }.padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ingrediente.nome,
            fontFamily = BebasNeue,
            fontSize = 15.sp,
            color = if (ingrediente.incluso) HomeColors.TextoBranco else HomeColors.TextoCinza,
            textDecoration = if (!ingrediente.incluso) TextDecoration.LineThrough else TextDecoration.None
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (ingrediente.incluso) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Incluso",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Incluso",
                    fontFamily = BebasNeue,
                    fontSize = 13.sp,
                    color = Color(0xFF4CAF50)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Removido",
                    tint = HomeColors.TextoCinza,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Removido",
                    fontFamily = BebasNeue,
                    fontSize = 13.sp,
                    color = HomeColors.TextoCinza
                )
            }
        }
    }
}
@Composable
private fun AdicionalRow(
    adicional: AdicionalAlimentacao,
    onToggle: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (adicional.selecionado) HomeColors.DetalhesCard1 else HomeColors.CardEscuro,
        label = "adicional_bg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (adicional.selecionado) HomeColors.Cards1 else Color.Transparent,
        label = "adicional_border"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (adicional.selecionado) HomeColors.Cards1
                        else HomeColors.Fundo
                    )
                    .border(
                        width = 1.5.dp,
                        color = if (adicional.selecionado) HomeColors.Cards1
                                else HomeColors.TextoCinza,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (adicional.selecionado) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = adicional.nome,
                fontFamily = BebasNeue,
                fontSize = 15.sp,
                color = if (adicional.selecionado) HomeColors.TextoBranco
                        else HomeColors.TextoCinza
            )
        }

        Text(
            text = "+ R$ ${String.format("%.2f", adicional.preco)}",
            fontFamily = BebasNeue,
            fontSize = 14.sp,
            color = if (adicional.selecionado) HomeColors.Cards1
                    else HomeColors.TextoCinza,
            fontWeight = if (adicional.selecionado) FontWeight.Bold else FontWeight.Normal
        )
    }
}