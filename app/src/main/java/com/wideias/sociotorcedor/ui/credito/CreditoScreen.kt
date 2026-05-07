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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import com.wideias.sociotorcedor.R

private object CreditoColors {
    val Fundo          = HomeColors.Fundo
    val CardClaro      = HomeColors.CardClaro
    val CardEscuro     = HomeColors.CardEscuro
    val Destaque       = HomeColors.Cards1        
    val TextoBranco    = HomeColors.TextoBranco
    val Preto          = HomeColors.Preto
    val DetalheCard    = HomeColors.DetalhesCard1
}

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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = CreditoColors.Fundo
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
                text = "ADICIONAR CRÉDITO",
                fontFamily = BebasNeue,
                fontSize = 26.sp,
                color = CreditoColors.TextoBranco,
                letterSpacing = 2.sp
            )

            SaldoCard(
                nome   = usuario?.nome ?: "N/A",
                saldo  = saldo
            )

            ValorSection(
                valorDigitado  = valorDigitado,
                onValorChange  = { valorDigitado = it }
            )

            PagamentoSection(
                selecionado   = formaPagamento,
                onSelecionar  = { formaPagamento = it }
            )

            val valorNumerico = valorDigitado
                .replace(",", ".")
                .toDoubleOrNull() ?: 0.0

            val habilitado = valorNumerico > 0.0 && formaPagamento != null

            Button(
                onClick = {
                    userViewModel.adicionarSaldo(valorNumerico)
                    valorDigitado  = ""
                    formaPagamento = null
                    mostrarConfirmado = true
                },
                enabled = habilitado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CreditoColors.Destaque,
                    disabledContainerColor = CreditoColors.Destaque.copy(alpha = 0.35f)
                )
            ) {
                Text(
                    text = "CONFIRMAR PAGAMENTO",
                    fontFamily = BebasNeue,
                    fontSize = 18.sp,
                    letterSpacing = 1.5.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ---------------------------------------------------------------------------
// Card de saldo
// ---------------------------------------------------------------------------
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
                .background(CreditoColors.CardClaro)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text       = "SALDO DISPONÍVEL",
                    fontFamily = BebasNeue,
                    fontSize   = 12.sp,
                    color      = CreditoColors.Preto.copy(alpha = 0.55f),
                    letterSpacing = 1.sp
                )
                Text(
                    text       = saldo,
                    fontFamily = BebasNeue,
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color      = CreditoColors.Preto
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text       = "USUÁRIO",
                    fontFamily = BebasNeue,
                    fontSize   = 12.sp,
                    color      = CreditoColors.Preto.copy(alpha = 0.55f),
                    letterSpacing = 1.sp
                )
                Text(
                    text       = nome,
                    fontFamily = BebasNeue,
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = CreditoColors.Preto
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Seção de valor
// ---------------------------------------------------------------------------
@Composable
private fun ValorSection(
    valorDigitado: String,
    onValorChange: (String) -> Unit
) {
    val atalhos = listOf("10,00", "25,00", "50,00")

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text       = "VALOR A ADICIONAR",
            fontFamily = BebasNeue,
            fontSize   = 14.sp,
            color      = CreditoColors.TextoBranco.copy(alpha = 0.7f),
            letterSpacing = 1.sp
        )

        // Campo de digitação
        OutlinedTextField(
            value         = valorDigitado,
            onValueChange = { novo ->
                // Aceita apenas números e vírgula/ponto
                if (novo.matches(Regex("^\\d{0,6}([,.]\\d{0,2})?\$"))) {
                    onValorChange(novo)
                }
            },
            modifier      = Modifier.fillMaxWidth(),
            placeholder   = {
                Text(
                    text       = "0,00",
                    fontFamily = BebasNeue,
                    fontSize   = 22.sp,
                    color      = Color.White.copy(alpha = 0.3f)
                )
            },
            prefix = {
                Text(
                    text       = "R\$  ",
                    fontFamily = BebasNeue,
                    fontSize   = 22.sp,
                    color      = CreditoColors.Destaque
                )
            },
            textStyle  = LocalTextStyle.current.copy(
                fontFamily = BebasNeue,
                fontSize   = 22.sp,
                color      = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine  = true,
            shape       = RoundedCornerShape(12.dp),
            colors      = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = CreditoColors.Destaque,
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                cursorColor          = CreditoColors.Destaque,
                focusedContainerColor   = Color.White.copy(alpha = 0.05f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.05f)
            )
        )

        // Botões de atalho
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            atalhos.forEach { valor ->
                val selecionado = valorDigitado == valor
                OutlinedButton(
                    onClick  = { onValorChange(valor) },
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(10.dp),
                    border   = BorderStroke(
                        1.5.dp,
                        if (selecionado) CreditoColors.Destaque else Color.White.copy(alpha = 0.25f)
                    ),
                    colors   = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selecionado)
                            CreditoColors.Destaque.copy(alpha = 0.15f)
                        else
                            Color.Transparent
                    )
                ) {
                    Text(
                        text       = "R\$ $valor",
                        fontFamily = BebasNeue,
                        fontSize   = 15.sp,
                        color      = if (selecionado) CreditoColors.Destaque else Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Seção de formas de pagamento
// ---------------------------------------------------------------------------
@Composable
private fun PagamentoSection(
    selecionado: String?,
    onSelecionar: (String) -> Unit
) {
    val opcoes = listOf(
        PagamentoOpcao("PIX",         "pix"),
        PagamentoOpcao("Samsung Pay", "samsung"),
        PagamentoOpcao("Apple Pay",   "apple"),
        PagamentoOpcao("Google Pay",  "google")
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text       = "FORMA DE PAGAMENTO",
            fontFamily = BebasNeue,
            fontSize   = 14.sp,
            color      = CreditoColors.TextoBranco.copy(alpha = 0.7f),
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
                            if (ativa) CreditoColors.Destaque.copy(alpha = 0.15f)
                            else Color.White.copy(alpha = 0.05f)
                        )
                        .border(
                            width  = 1.5.dp,
                            color  = if (ativa) CreditoColors.Destaque else Color.White.copy(alpha = 0.12f),
                            shape  = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelecionar(opcao.id) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text       = opcao.label,
                        fontFamily = BebasNeue,
                        fontSize   = 17.sp,
                        color      = if (ativa) CreditoColors.Destaque else Color.White.copy(alpha = 0.85f),
                        letterSpacing = 0.5.sp
                    )

                    // Indicador de seleção
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(
                                if (ativa) CreditoColors.Destaque else Color.Transparent
                            )
                            .border(
                                width  = 2.dp,
                                color  = if (ativa) CreditoColors.Destaque else Color.White.copy(alpha = 0.3f),
                                shape  = CircleShape
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
    }
}

private data class PagamentoOpcao(val label: String, val id: String)