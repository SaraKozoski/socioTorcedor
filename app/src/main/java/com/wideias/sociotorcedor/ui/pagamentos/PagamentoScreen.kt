package com.wideias.sociotorcedor.ui.pagamentos

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.ui.alimentacao.CarrinhoViewModel
import com.wideias.sociotorcedor.viewmodel.UserViewModel
import kotlinx.coroutines.launch

private data class OpcaoPagamento(val label: String, val id: String, val descricao: String = "")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagamentoScreen(
    navController: NavController,
    carrinhoViewModel: CarrinhoViewModel,
    userViewModel: UserViewModel
) {
    val itens by carrinhoViewModel.itens.collectAsState()
    val saldoNumerico by userViewModel.saldoNumerico.collectAsState()
    val saldoFormatado by userViewModel.saldo.collectAsState()

    val total = carrinhoViewModel.precoTotal
    val itensAgrupados = itens.groupBy { it.produto.id }

    var formaPagamento by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val saldoInsuficiente = formaPagamento == "saldo" && saldoNumerico < total

    val opcoes = listOf(
        OpcaoPagamento("Saldo / Crédito", "saldo", "Disponível: $saldoFormatado"),
        OpcaoPagamento("PIX", "pix"),
        OpcaoPagamento("Google Pay", "google"),
        OpcaoPagamento("Samsung Pay", "samsung"),
        OpcaoPagamento("Apple Pay", "apple"),
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = HomeColors.Fundo,
        bottomBar = {
            Surface(color = HomeColors.CardEscuro, shadowElevation = 12.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (saldoInsuficiente) {
                        Text(
                            text = "Saldo insuficiente para este pedido.",
                            fontFamily = BebasNeue,
                            fontSize = 13.sp,
                            color = Color(0xFFE53935),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "TOTAL DO PEDIDO",
                                fontFamily = BebasNeue,
                                fontSize = 12.sp,
                                color = HomeColors.TextoCinza
                            )
                            Text(
                                text = "R$ ${String.format("%.2f", total)}",
                                fontFamily = BebasNeue,
                                fontSize = 22.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    if (formaPagamento == "saldo") {
                                        userViewModel.debitarSaldo(total)
                                    } else {
                                        userViewModel.adicionarPontos(total)
                                    }
                                    carrinhoViewModel.limparCarrinho()
                                    snackbarHostState.showSnackbar("Pedido realizado com sucesso!")
                                    navController.popBackStack()
                                    navController.popBackStack()
                                }
                            },
                            enabled = formaPagamento != null && !saldoInsuficiente,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HomeColors.Cards1,
                                disabledContainerColor = HomeColors.Cards1.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier
                                .height(48.dp)
                                .widthIn(min = 160.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CONFIRMAR PEDIDO",
                                fontFamily = BebasNeue,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            SectionTitle("RESUMO DO PEDIDO")

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = HomeColors.CardEscuro),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                    itensAgrupados.entries.forEachIndexed { index, (_, grupo) ->
                        val item = grupo.first()
                        val qtd = grupo.size

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = item.produto.imagemRes,
                                contentDescription = item.produto.nome,
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${qtd}x ${item.produto.nome.uppercase()}",
                                    fontFamily = BebasNeue,
                                    fontSize = 14.sp,
                                    color = HomeColors.TextoBranco
                                )
                                if (item.adicionaisSelecionados.isNotEmpty()) {
                                    Text(
                                        text = item.adicionaisSelecionados.joinToString(", ") { it.nome },
                                        fontFamily = BebasNeue,
                                        fontSize = 11.sp,
                                        color = HomeColors.TextoCinza
                                    )
                                }
                            }

                            Text(
                                text = "R$ ${String.format("%.2f", item.precoTotal * qtd)}",
                                fontFamily = BebasNeue,
                                fontSize = 14.sp,
                                color = HomeColors.Cards1,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (index < itensAgrupados.size - 1) {
                            HorizontalDivider(
                                color = HomeColors.Fundo.copy(alpha = 0.6f),
                                thickness = 0.5.dp
                            )
                        }
                    }

                    HorizontalDivider(
                        color = HomeColors.Fundo,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TOTAL",
                            fontFamily = BebasNeue,
                            fontSize = 16.sp,
                            color = HomeColors.TextoCinza
                        )
                        Text(
                            text = "R$ ${String.format("%.2f", total)}",
                            fontFamily = BebasNeue,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            SectionTitle("FORMA DE PAGAMENTO")

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                opcoes.forEach { opcao ->
                    val ativa = formaPagamento == opcao.id
                    val ehSaldo = opcao.id == "saldo"
                    val semSaldo = ehSaldo && saldoNumerico < total

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                when {
                                    ativa -> HomeColors.Cards1.copy(alpha = 0.15f)
                                    semSaldo -> Color.White.copy(alpha = 0.02f)
                                    else -> Color.White.copy(alpha = 0.05f)
                                }
                            )
                            .border(
                                width = 1.5.dp,
                                color = when {
                                    ativa -> HomeColors.Cards1
                                    semSaldo -> Color.White.copy(alpha = 0.06f)
                                    else -> Color.White.copy(alpha = 0.12f)
                                },
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(enabled = !semSaldo) { formaPagamento = opcao.id }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = opcao.label,
                                fontFamily = BebasNeue,
                                fontSize = 17.sp,
                                color = when {
                                    ativa -> HomeColors.Cards1
                                    semSaldo -> HomeColors.TextoCinza.copy(alpha = 0.5f)
                                    else -> Color.White.copy(alpha = 0.85f)
                                },
                                letterSpacing = 0.5.sp
                            )
                            if (opcao.descricao.isNotBlank()) {
                                Text(
                                    text = if (semSaldo) "Saldo insuficiente" else opcao.descricao,
                                    fontFamily = BebasNeue,
                                    fontSize = 12.sp,
                                    color = if (semSaldo) Color(0xFFE53935).copy(alpha = 0.7f)
                                            else HomeColors.TextoCinza
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(if (ativa) HomeColors.Cards1 else Color.Transparent)
                                .border(
                                    width = 2.dp,
                                    color = when {
                                        ativa -> HomeColors.Cards1
                                        semSaldo -> Color.White.copy(alpha = 0.12f)
                                        else -> Color.White.copy(alpha = 0.3f)
                                    },
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (ativa) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SectionTitle(titulo: String) {
    Text(
        text = titulo,
        fontFamily = BebasNeue,
        fontSize = 14.sp,
        color = HomeColors.TextoBranco.copy(alpha = 0.7f),
        letterSpacing = 1.sp
    )
}