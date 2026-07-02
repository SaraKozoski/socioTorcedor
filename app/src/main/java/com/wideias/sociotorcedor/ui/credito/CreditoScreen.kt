package com.wideias.sociotorcedor.ui.credito

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState

// Sem objeto CreditoColors — usa HomeColors diretamente (única fonte de verdade).

@Composable
fun CreditoScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val usuario by userViewModel.usuario.collectAsState()
    val saldo   by userViewModel.saldo.collectAsState()

    var valorDigitado     by remember { mutableStateOf("") }
    var formaPagamento    by remember { mutableStateOf<String?>(null) }
    var mostrarConfirmado by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mostrarConfirmado) {
        if (mostrarConfirmado) {
            snackbarHostState.showSnackbar("Crédito adicionado com sucesso!")
            mostrarConfirmado = false
        }
    }

    Scaffold(
        snackbarHost    = { SnackbarHost(snackbarHostState) },
        containerColor  = HomeColors.Fundo
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text          = "ADICIONAR CRÉDITO",
                fontFamily    = BebasNeue,
                fontSize      = 26.sp,
                color         = HomeColors.TextoBranco,
                letterSpacing = 2.sp
            )

            SaldoCard(
                nome  = usuario?.nome ?: "N/A",
                saldo = saldo
            )

            ValorSection(
                valorDigitado = valorDigitado,
                onValorChange = { valorDigitado = it }
            )

            PagamentoSection(
                selecionado  = formaPagamento,
                onSelecionar = { formaPagamento = it }
            )

            val valorNumerico = valorDigitado.replace(",", ".").toDoubleOrNull() ?: 0.0
            val habilitado    = valorNumerico > 0.0 && formaPagamento != null

            Button(
                onClick = {
                    userViewModel.adicionarSaldo(valorNumerico)
                    valorDigitado  = ""
                    formaPagamento = null
                    mostrarConfirmado = true
                },
                enabled  = habilitado,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = HomeColors.Cards1,
                    disabledContainerColor = HomeColors.Cards1.copy(alpha = 0.35f)
                )
            ) {
                Text(
                    text          = "CONFIRMAR PAGAMENTO",
                    fontFamily    = BebasNeue,
                    fontSize      = 18.sp,
                    letterSpacing = 1.5.sp,
                    color         = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ── Card de saldo ─────────────────────────────────────────────────────────────

@Composable
private fun SaldoCard(nome: String, saldo: String) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HomeColors.CardClaro)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text          = "SALDO DISPONÍVEL",
                    fontFamily    = BebasNeue,
                    fontSize      = 12.sp,
                    color         = HomeColors.Preto.copy(alpha = 0.55f),
                    letterSpacing = 1.sp
                )
                Text(
                    text       = saldo,
                    fontFamily = BebasNeue,
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color      = HomeColors.Preto
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text          = "USUÁRIO",
                    fontFamily    = BebasNeue,
                    fontSize      = 12.sp,
                    color         = HomeColors.Preto.copy(alpha = 0.55f),
                    letterSpacing = 1.sp
                )
                Text(
                    text       = nome,
                    fontFamily = BebasNeue,
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = HomeColors.Preto
                )
            }
        }
    }
}

// ── Seção de valor ────────────────────────────────────────────────────────────

@Composable
private fun ValorSection(
    valorDigitado: String,
    onValorChange: (String) -> Unit
) {
    val atalhos = listOf("10,00", "25,00", "50,00")

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text          = "VALOR A ADICIONAR",
            fontFamily    = BebasNeue,
            fontSize      = 14.sp,
            color         = HomeColors.TextoBranco.copy(alpha = 0.7f),
            letterSpacing = 1.sp
        )

        OutlinedTextField(
            value         = valorDigitado,
            onValueChange = { novo ->
                if (novo.matches(Regex("^\\d{0,6}([,.]\\d{0,2})?\$"))) onValorChange(novo)
            },
            modifier    = Modifier.fillMaxWidth(),
            placeholder = {
                Text("0,00", fontFamily = BebasNeue, fontSize = 22.sp,
                    color = Color.White.copy(alpha = 0.3f))
            },
            prefix = {
                Text("R\$  ", fontFamily = BebasNeue, fontSize = 22.sp,
                    color = HomeColors.Cards1)
            },
            textStyle       = LocalTextStyle.current.copy(fontFamily = BebasNeue, fontSize = 22.sp, color = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine      = true,
            shape           = RoundedCornerShape(12.dp),
            colors          = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = HomeColors.Cards1,
                unfocusedBorderColor    = Color.White.copy(alpha = 0.2f),
                cursorColor             = HomeColors.Cards1,
                focusedContainerColor   = Color.White.copy(alpha = 0.05f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.05f)
            )
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            atalhos.forEach { valor ->
                val selecionado = valorDigitado == valor
                OutlinedButton(
                    onClick  = { onValorChange(valor) },
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(10.dp),
                    border   = BorderStroke(1.5.dp,
                        if (selecionado) HomeColors.Cards1 else Color.White.copy(alpha = 0.25f)),
                    colors   = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selecionado) HomeColors.Cards1.copy(alpha = 0.15f)
                                         else Color.Transparent
                    )
                ) {
                    Text(
                        text       = "R\$ $valor",
                        fontFamily = BebasNeue,
                        fontSize   = 15.sp,
                        color      = if (selecionado) HomeColors.Cards1
                                     else Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

// ── Seção de formas de pagamento ──────────────────────────────────────────────

private data class OpcaoPagamento(val label: String, val id: String)

@Composable
private fun PagamentoSection(
    selecionado : String?,
    onSelecionar: (String) -> Unit
) {
    val opcoes = listOf(
        OpcaoPagamento("PIX",         "pix"),
        OpcaoPagamento("Samsung Pay", "samsung"),
        OpcaoPagamento("Apple Pay",   "apple"),
        OpcaoPagamento("Google Pay",  "google")
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text          = "FORMA DE PAGAMENTO",
            fontFamily    = BebasNeue,
            fontSize      = 14.sp,
            color         = HomeColors.TextoBranco.copy(alpha = 0.7f),
            letterSpacing = 1.sp
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            opcoes.forEach { opcao ->
                val ativa = selecionado == opcao.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (ativa) HomeColors.Cards1.copy(alpha = 0.15f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .border(
                            width  = 1.5.dp,
                            color  = if (ativa) HomeColors.Cards1 else Color.White.copy(alpha = 0.12f),
                            shape  = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelecionar(opcao.id) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text          = opcao.label,
                        fontFamily    = BebasNeue,
                        fontSize      = 17.sp,
                        color         = if (ativa) HomeColors.Cards1 else Color.White.copy(alpha = 0.85f),
                        letterSpacing = 0.5.sp
                    )

                    // Indicador de seleção (radio-style)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(if (ativa) HomeColors.Cards1 else Color.Transparent)
                            .border(2.dp,
                                if (ativa) HomeColors.Cards1 else Color.White.copy(alpha = 0.3f),
                                CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (ativa) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White))
                        }
                    }
                }
            }
        }
    }
}