package com.wideias.sociotorcedor.ui.meueespaco

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.ui.theme.FundoEscuro
import com.wideias.sociotorcedor.ui.theme.VermelhoBotao
import com.wideias.sociotorcedor.ui.theme.VermelhoFundoLogin
import com.wideias.sociotorcedor.viewmodel.UserViewModel

private val CardEscuro   = Color(0xFF1E1E1E)
private val TextoSecund  = Color(0xFFAAAAAA)
private val VerdeConfirm = Color(0xFF4CAF50)
private val AmareloFundo = Color(0xFFFFC107)

fun gerarQrCodeBitmap(conteudo: String, tamanho: Int = 512): Bitmap {
    val bitMatrix: BitMatrix = MultiFormatWriter().encode(
        conteudo, BarcodeFormat.QR_CODE, tamanho, tamanho
    )
    val bmp = Bitmap.createBitmap(tamanho, tamanho, Bitmap.Config.RGB_565)
    for (x in 0 until tamanho) {
        for (y in 0 until tamanho) {
            bmp.setPixel(
                x, y,
                if (bitMatrix[x, y]) android.graphics.Color.BLACK
                else android.graphics.Color.WHITE
            )
        }
    }
    return bmp
}

@Composable
fun MeuEspacoScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    viewModel: MeuEspacoViewModel = viewModel(
        factory = MeuEspacoViewModelFactory(userViewModel)
    )
) {
    val usuario by userViewModel.usuario.collectAsState()
    val state   by viewModel.state.collectAsState()

    LaunchedEffect(usuario) {
        if (usuario == null) {
            navController.navigate("login") {
                popUpTo("meueespaco") { inclusive = true }
            }
        }
    }

    var tabSelecionada by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoEscuro)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = "Meu Espaço",
                fontSize   = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BebasNeue,
                color      = Color.White
            )
            Text(
                text       = usuario?.nome ?: "",
                fontSize   = 14.sp,
                fontFamily = BebasNeue,
                color      = TextoSecund
            )
        }

        TabRow(
            selectedTabIndex = tabSelecionada,
            containerColor   = CardEscuro,
            contentColor     = VermelhoBotao,
            indicator        = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabSelecionada]),
                    color = VermelhoBotao
                )
            }
        ) {
            Tab(
                selected               = tabSelecionada == 0,
                onClick                = { tabSelecionada = 0 },
                icon                   = { Icon(Icons.Default.ConfirmationNumber, contentDescription = null) },
                text                   = { Text("Ingressos", fontFamily = BebasNeue) },
                selectedContentColor   = VermelhoBotao,
                unselectedContentColor = TextoSecund
            )
            Tab(
                selected               = tabSelecionada == 1,
                onClick                = { tabSelecionada = 1 },
                icon                   = { Icon(Icons.Default.LocalOffer, contentDescription = null) },
                text                   = { Text("Benefícios", fontFamily = BebasNeue) },
                selectedContentColor   = VermelhoBotao,
                unselectedContentColor = TextoSecund
            )
        }

        when (val s = state) {
            is MeuEspacoState.Carregando -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = VermelhoBotao)
                }
            }
            is MeuEspacoState.Erro -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        s.mensagem,
                        color      = Color.White,
                        fontFamily = BebasNeue,
                        textAlign  = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.recarregar() },
                        colors  = ButtonDefaults.buttonColors(containerColor = VermelhoBotao)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text("Tentar novamente", fontFamily = BebasNeue, color = Color.White)
                    }
                }
            }
            is MeuEspacoState.Sucesso -> {
                when (tabSelecionada) {
                    0 -> AbaIngressos(ingressos = s.ingressos, userViewModel = userViewModel)
                    1 -> AbaBeneficios(beneficios = s.beneficios)
                }
            }
        }
    }
}

@Composable
fun AbaIngressos(ingressos: List<Ingresso>, userViewModel: UserViewModel) {
    val listaLocal        = remember(ingressos) { ingressos.toMutableStateList() }
    var ingressoQr        by remember { mutableStateOf<Ingresso?>(null) }
    var ingressoConfirmar by remember { mutableStateOf<Ingresso?>(null) }

    Column(
        modifier            = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (listaLocal.isEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Nenhum ingresso encontrado.",
                    color      = TextoSecund,
                    fontFamily = BebasNeue,
                    fontSize   = 16.sp
                )
            }
        }

        listaLocal.forEach { ingresso ->
            CartaoIngresso(
                ingresso    = ingresso,
                onVerQr     = { ingressoQr = ingresso },
                onConfirmar = { ingressoConfirmar = ingresso }
            )
        }
    }

    ingressoQr?.let { ingresso ->
        DialogQrCode(ingresso = ingresso, onDismiss = { ingressoQr = null })
    }

    ingressoConfirmar?.let { ingresso ->
        DialogConfirmarParticipacao(
            ingresso      = ingresso,
            userViewModel = userViewModel,
            onConfirmar   = {
                val idx = listaLocal.indexOfFirst { it == ingresso }
                if (idx != -1) listaLocal[idx] = ingresso.copy(confirmado = true)
                ingressoConfirmar = null
            },
            onRecusar = {
                listaLocal.remove(ingresso)
                ingressoConfirmar = null
            }
        )
    }
}

@Composable
fun CartaoIngresso(
    ingresso    : Ingresso,
    onVerQr     : () -> Unit,
    onConfirmar : () -> Unit
) {
    val corStatus   = if (ingresso.confirmado) VerdeConfirm else AmareloFundo
    val textoStatus = if (ingresso.confirmado) "✓ Confirmado" else "⏳ Pendente"

    Card(
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = CardEscuro),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text       = ingresso.jogo,
                    color      = Color.White,
                    fontSize   = 15.sp,
                    fontFamily = BebasNeue,
                    fontWeight = FontWeight.Bold,
                    modifier   = Modifier.weight(1f)
                )
                Surface(
                    shape    = RoundedCornerShape(50.dp),
                    color    = corStatus.copy(alpha = 0.15f),
                    modifier = if (!ingresso.confirmado)
                        Modifier.clickable { onConfirmar() }
                    else
                        Modifier
                ) {
                    Text(
                        text       = textoStatus,
                        color      = corStatus,
                        fontSize   = 11.sp,
                        fontFamily = BebasNeue,
                        modifier   = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            Spacer(Modifier.height(8.dp))

            InfoLinha("📅 Data",    "${ingresso.data} às ${ingresso.hora}")
            InfoLinha("📍 Setor",   ingresso.setor)
            InfoLinha("💺 Assento", ingresso.assento)

            Spacer(Modifier.height(12.dp))

            if (ingresso.confirmado) {
                Button(
                    onClick  = onVerQr,
                    shape    = RoundedCornerShape(25.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Icon(Icons.Default.QrCode, contentDescription = null, tint = Color.Black)
                    Spacer(Modifier.width(8.dp))
                    Text("Ver QR Code de Entrada", fontFamily = BebasNeue, color = Color.Black)
                }
            } else {
                OutlinedButton(
                    onClick  = onConfirmar,
                    shape    = RoundedCornerShape(25.dp),
                    border   = BorderStroke(1.dp, AmareloFundo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text("Confirmar Participação", fontFamily = BebasNeue, color = AmareloFundo)
                }
            }
        }
    }
}

@Composable
fun DialogConfirmarParticipacao(
    ingresso      : Ingresso,
    userViewModel : UserViewModel,
    onConfirmar   : () -> Unit,
    onRecusar     : () -> Unit
) {
    var etapa        by remember { mutableIntStateOf(1) }
    var senha        by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var erroSenha    by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onRecusar) {
        Card(
            shape  = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardEscuro)
        ) {
            Column(
                modifier            = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (etapa == 1) {

                    Surface(
                        shape    = RoundedCornerShape(50.dp),
                        color    = AmareloFundo.copy(alpha = 0.15f),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector        = Icons.Default.ConfirmationNumber,
                                contentDescription = null,
                                tint               = AmareloFundo,
                                modifier           = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text       = "Confirmar Presença?",
                        fontSize   = 22.sp,
                        fontFamily = BebasNeue,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        ingresso.jogo,
                        fontSize  = 14.sp,
                        fontFamily = BebasNeue,
                        color     = TextoSecund,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "${ingresso.data} às ${ingresso.hora}",
                        fontSize  = 12.sp,
                        fontFamily = BebasNeue,
                        color     = TextoSecund,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "${ingresso.setor} · ${ingresso.assento}",
                        fontSize  = 12.sp,
                        fontFamily = BebasNeue,
                        color     = TextoSecund,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick  = { etapa = 2 },
                        shape    = RoundedCornerShape(25.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = VerdeConfirm),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            "✓  Sim, vou comparecer",
                            fontFamily = BebasNeue,
                            color      = Color.White,
                            fontSize   = 16.sp
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    OutlinedButton(
                        onClick  = onRecusar,
                        shape    = RoundedCornerShape(25.dp),
                        border   = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            "Não poderei ir",
                            fontFamily = BebasNeue,
                            color      = TextoSecund,
                            fontSize   = 16.sp
                        )
                    }

                } else {

                    Surface(
                        shape    = RoundedCornerShape(50.dp),
                        color    = VermelhoBotao.copy(alpha = 0.15f),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector        = Icons.Default.Lock,
                                contentDescription = null,
                                tint               = VermelhoBotao,
                                modifier           = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text       = "Digite sua senha",
                        fontSize   = 22.sp,
                        fontFamily = BebasNeue,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )

                    Text(
                        text      = "Para confirmar sua presença,\ninsira a senha da sua conta.",
                        fontSize  = 13.sp,
                        fontFamily = BebasNeue,
                        color     = TextoSecund,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.padding(top = 4.dp, bottom = 20.dp)
                    )

                    OutlinedTextField(
                        value         = senha,
                        onValueChange = { senha = it; erroSenha = false },
                        placeholder   = { Text("Senha", fontFamily = BebasNeue, color = TextoSecund) },
                        singleLine    = true,
                        isError       = erroSenha,
                        visualTransformation = if (senhaVisivel)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                                Icon(
                                    imageVector        = if (senhaVisivel)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint               = TextoSecund
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = VermelhoBotao,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedTextColor     = Color.White,
                            unfocusedTextColor   = Color.White,
                            errorBorderColor     = Color(0xFFFF5252),
                            cursorColor          = VermelhoBotao
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (erroSenha) {
                        Text(
                            text       = "Senha incorreta. Tente novamente.",
                            color      = Color(0xFFFF5252),
                            fontSize   = 11.sp,
                            fontFamily = BebasNeue,
                            modifier   = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick  = {
                            if (userViewModel.verificarSenha(senha)) {
                                onConfirmar()
                            } else {
                                erroSenha = true
                                senha     = ""
                            }
                        },
                        shape    = RoundedCornerShape(25.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled  = senha.isNotBlank()
                    ) {
                        Text(
                            "Confirmar",
                            fontFamily = BebasNeue,
                            color      = Color.Black,
                            fontSize   = 16.sp
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    TextButton(onClick = { etapa = 1; senha = ""; erroSenha = false }) {
                        Text(
                            "← Voltar",
                            fontFamily = BebasNeue,
                            color      = TextoSecund,
                            fontSize   = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoLinha(label: String, valor: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            label,
            color    = TextoSecund,
            fontSize = 12.sp,
            fontFamily = BebasNeue,
            modifier = Modifier.width(90.dp)
        )
        Text(valor, color = Color.White, fontSize = 12.sp, fontFamily = BebasNeue)
    }
}

@Composable
fun DialogQrCode(ingresso: Ingresso, onDismiss: () -> Unit) {
    val qrBitmap = remember(ingresso.qrCodeData) { gerarQrCodeBitmap(ingresso.qrCodeData) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape  = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardEscuro)
        ) {
            Column(
                modifier            = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text       = "QR Code de Entrada",
                    fontSize   = 20.sp,
                    fontFamily = BebasNeue,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White
                )
                Text(
                    text      = ingresso.jogo,
                    fontSize  = 13.sp,
                    fontFamily = BebasNeue,
                    color     = TextoSecund,
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                Image(
                    bitmap             = qrBitmap.asImageBitmap(),
                    contentDescription = "QR Code de entrada",
                    modifier           = Modifier
                        .size(220.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text      = "${ingresso.setor} · ${ingresso.assento}",
                    fontSize  = 12.sp,
                    fontFamily = BebasNeue,
                    color     = TextoSecund,
                    textAlign = TextAlign.Center
                )
                Text(
                    ingresso.data,
                    fontSize  = 12.sp,
                    fontFamily = BebasNeue,
                    color     = TextoSecund
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick  = onDismiss,
                    shape    = RoundedCornerShape(25.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text("Fechar", fontFamily = BebasNeue, color = Color.Black, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun AbaBeneficios(beneficios: List<Beneficio>) {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (beneficios.isEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Nenhum benefício disponível.",
                    color      = TextoSecund,
                    fontFamily = BebasNeue,
                    fontSize   = 16.sp
                )
            }
        }
        beneficios.forEach { CartaoBeneficio(it) }
    }
}

@Composable
fun CartaoBeneficio(beneficio: Beneficio) {
    val clipboard = LocalClipboardManager.current
    var copiado   by remember { mutableStateOf(false) }

    Card(
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = CardEscuro),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text       = beneficio.titulo,
                    color      = Color.White,
                    fontSize   = 16.sp,
                    fontFamily = BebasNeue,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = VermelhoBotao.copy(alpha = 0.2f)
                ) {
                    Text(
                        text       = beneficio.desconto,
                        color      = VermelhoBotao,
                        fontSize   = 13.sp,
                        fontFamily = BebasNeue,
                        fontWeight = FontWeight.Bold,
                        modifier   = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(6.dp))
            Text(beneficio.descricao, color = TextoSecund, fontSize = 12.sp, fontFamily = BebasNeue)

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.08f))
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FundoEscuro, RoundedCornerShape(10.dp))
                    .border(1.dp, VermelhoFundoLogin, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text          = beneficio.codigo,
                    color         = Color.White,
                    fontSize      = 18.sp,
                    fontFamily    = BebasNeue,
                    fontWeight    = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                IconButton(
                    onClick  = {
                        clipboard.setText(AnnotatedString(beneficio.codigo))
                        copiado = true
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Default.ContentCopy,
                        contentDescription = "Copiar código",
                        tint               = if (copiado) VerdeConfirm else TextoSecund,
                        modifier           = Modifier.size(18.dp)
                    )
                }
            }

            if (copiado) {
                Text(
                    "✓ Código copiado!",
                    color    = VerdeConfirm,
                    fontSize = 11.sp,
                    fontFamily = BebasNeue,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(Modifier.height(6.dp))
            Text(
                "Válido até: ${beneficio.validade}",
                color      = TextoSecund,
                fontSize   = 11.sp,
                fontFamily = BebasNeue
            )
        }
    }
}