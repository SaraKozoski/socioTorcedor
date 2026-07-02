package com.wideias.sociotorcedor.ui.perfil

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.ui.theme.FundoEscuro
import com.wideias.sociotorcedor.ui.theme.VermelhoBotao
import com.wideias.sociotorcedor.ui.theme.VermelhoFundoLogin
import com.wideias.sociotorcedor.viewmodel.UserViewModel


data class Dependente(
    val id: Int,
    var nome: String = "",
    var dataNascimento: String = "",
    var cpf: String = "",
    var grauParentesco: String = ""
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    onVoltarClick: () -> Unit,
    onEsqueciSenhaClick: () -> Unit
) {
    var nome by remember { mutableStateOf("João da Silva") }
    var email by remember { mutableStateOf("joao@email.com") }
    var dataNascimento by remember { mutableStateOf("01/01/1990") }
    var cpf by remember { mutableStateOf("12345678901") }
    var telefone by remember { mutableStateOf("47999999999") }
    var fotoUri by remember { mutableStateOf<String?>(null) }

    var editandoEmail by remember { mutableStateOf(false) }
    var novoEmail by remember { mutableStateOf("") }
    var confirmacaoEmail by remember { mutableStateOf("") }
    var editandoTelefone by remember { mutableStateOf(false) }
    var emailErro by remember { mutableStateOf<String?>(null) }

    var dependentes by remember {
        mutableStateOf(
            listOf(
                Dependente(id = 1, nome = "Maria da Silva", dataNascimento = "10/05/2015", cpf = "98765432100", grauParentesco = "Filha")
            )
        )
    }
    var mostrarDialogDependente by remember { mutableStateOf(false) }
    var dependenteEditando by remember { mutableStateOf<Dependente?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { fotoUri = it.toString() } }

    if (editandoEmail) {
        AlertDialog(
            onDismissRequest = { editandoEmail = false; emailErro = null },
            containerColor = FundoEscuro,
            title = {
                Text("Alterar E-mail", fontFamily = BebasNeue, color = Color.White, fontSize = 20.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CampoTexto(
                        label = "Novo e-mail",
                        value = novoEmail,
                        onValueChange = { novoEmail = it; emailErro = null },
                        keyboardType = KeyboardType.Email
                    )
                    CampoTexto(
                        label = "Confirmar e-mail",
                        value = confirmacaoEmail,
                        onValueChange = { confirmacaoEmail = it; emailErro = null },
                        keyboardType = KeyboardType.Email
                    )
                    emailErro?.let {
                        Text(it, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, fontFamily = BebasNeue)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        when {
                            novoEmail.isBlank() || confirmacaoEmail.isBlank() ->
                                emailErro = "Preencha todos os campos."
                            novoEmail != confirmacaoEmail ->
                                emailErro = "Os e-mails não coincidem."
                            !android.util.Patterns.EMAIL_ADDRESS.matcher(novoEmail).matches() ->
                                emailErro = "E-mail inválido."
                            else -> {
                                email = novoEmail
                                novoEmail = ""
                                confirmacaoEmail = ""
                                editandoEmail = false
                                emailErro = null
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Confirmar", fontFamily = BebasNeue, color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { editandoEmail = false; emailErro = null }) {
                    Text("Cancelar", fontFamily = BebasNeue, color = Color.White)
                }
            }
        )
    }

    if (mostrarDialogDependente) {
        val dep = dependenteEditando ?: Dependente(id = dependentes.size + 1)
        var depNome by remember { mutableStateOf(dep.nome) }
        var depData by remember { mutableStateOf(dep.dataNascimento) }
        var depCpf by remember { mutableStateOf(dep.cpf) }
        var depGrau by remember { mutableStateOf(dep.grauParentesco) }

        AlertDialog(
            onDismissRequest = { mostrarDialogDependente = false; dependenteEditando = null },
            containerColor = FundoEscuro,
            title = {
                Text(
                    if (dependenteEditando == null) "Adicionar Dependente" else "Editar Dependente",
                    fontFamily = BebasNeue, color = Color.White, fontSize = 20.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CampoTexto("Nome completo", depNome, { depNome = it })
                    CampoTexto("Data de nascimento", depData, {
                        depData = formatarData(it, depData)
                    }, keyboardType = KeyboardType.Number)
                    CampoTexto("CPF", depCpf, {
                        if (it.length <= 11) depCpf = it.filter { c -> c.isDigit() }
                    }, keyboardType = KeyboardType.Number)
                    CampoTexto("Grau de parentesco", depGrau, { depGrau = it })
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val novo = Dependente(dep.id, depNome, depData, depCpf, depGrau)
                        dependentes = if (dependenteEditando == null) {
                            dependentes + novo
                        } else {
                            dependentes.map { if (it.id == dep.id) novo else it }
                        }
                        mostrarDialogDependente = false
                        dependenteEditando = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Salvar", fontFamily = BebasNeue, color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogDependente = false; dependenteEditando = null }) {
                    Text("Cancelar", fontFamily = BebasNeue, color = Color.White)
                }
            }
        )
    }

    Scaffold(
        containerColor = FundoEscuro
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(VermelhoFundoLogin)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (fotoUri != null) {
                    AsyncImage(
                        model = fotoUri,
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Sem foto",
                        tint = Color.White,
                        modifier = Modifier.size(56.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Alterar foto",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = nome,
                fontFamily = BebasNeue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            SecaoTitulo("Dados Pessoais")

            CampoSoLeitura(label = "Nome completo", valor = nome)

            CampoSoLeitura(label = "CPF", valor = cpf.formatarCpf())

            CampoSoLeitura(label = "Data de Nascimento", valor = dataNascimento)

            CampoComBotao(
                label = "E-mail",
                valor = email,
                icone = Icons.Default.Edit,
                onAcaoClick = {
                    novoEmail = ""
                    confirmacaoEmail = ""
                    editandoEmail = true
                }
            )

            if (editandoTelefone) {
                CampoTexto(
                    label = "Telefone",
                    value = telefone,
                    onValueChange = {
                        if (it.length <= 11) telefone = it.filter { c -> c.isDigit() }
                    },
                    keyboardType = KeyboardType.Phone,
                    trailingIcon = {
                        IconButton(onClick = { editandoTelefone = false }) {
                            Icon(Icons.Default.Check, contentDescription = "Confirmar", tint = VermelhoBotao)
                        }
                    }
                )
            } else {
                CampoComBotao(
                    label = "Telefone",
                    valor = telefone.formatarTelefone(),
                    icone = Icons.Default.Edit,
                    onAcaoClick = { editandoTelefone = true }
                )
            }

            CampoSoLeitura(label = "Senha", valor = "••••••••")

            TextButton(
                onClick = onEsqueciSenhaClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    "Esqueci minha senha",
                    fontFamily = BebasNeue,
                    color = VermelhoBotao,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SecaoTitulo("Dependentes", modifier = Modifier)
                TextButton(onClick = {
                    dependenteEditando = null
                    mostrarDialogDependente = true
                }) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Adicionar dependente",
                        tint = VermelhoBotao,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Adicionar", fontFamily = BebasNeue, color = VermelhoBotao, fontSize = 14.sp)
                }
            }

            if (dependentes.isEmpty()) {
                Text(
                    "Nenhum dependente cadastrado.",
                    fontFamily = BebasNeue,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                dependentes.forEach { dep ->
                    CartaoDependente(
                        dependente = dep,
                        onEditar = {
                            dependenteEditando = dep
                            mostrarDialogDependente = true
                        },
                        onRemover = {
                            dependentes = dependentes.filter { it.id != dep.id }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* TODO: chamar ViewModel.salvarPerfil() */ },
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Salvar Alterações", fontSize = 16.sp, color = Color.Black, fontFamily = BebasNeue)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
private fun SecaoTitulo(titulo: String, modifier: Modifier = Modifier.fillMaxWidth()) {
    Text(
        text = titulo.uppercase(),
        fontFamily = BebasNeue,
        fontSize = 16.sp,
        color = VermelhoBotao,
        letterSpacing = 1.5.sp,
        modifier = modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun CampoSoLeitura(label: String, valor: String) {
    OutlinedTextField(
        value = valor,
        onValueChange = {},
        label = { Text(label, fontFamily = BebasNeue) },
        textStyle = TextStyle(fontFamily = BebasNeue, color = Color.White),
        readOnly = true,
        singleLine = true,
        shape = RoundedCornerShape(25.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = VermelhoFundoLogin,
            unfocusedContainerColor = VermelhoFundoLogin,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            disabledContainerColor = VermelhoFundoLogin,
            disabledTextColor = Color.White,
            disabledBorderColor = Color.Black,
            disabledLabelColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}

@Composable
private fun CampoComBotao(
    label: String,
    valor: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    onAcaoClick: () -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = {},
        label = { Text(label, fontFamily = BebasNeue) },
        textStyle = TextStyle(fontFamily = BebasNeue, color = Color.White),
        readOnly = true,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onAcaoClick) {
                Icon(icone, contentDescription = "Editar $label", tint = Color.White)
            }
        },
        shape = RoundedCornerShape(25.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = VermelhoFundoLogin,
            unfocusedContainerColor = VermelhoFundoLogin,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}

@Composable
private fun CampoTexto(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontFamily = BebasNeue) },
        textStyle = TextStyle(fontFamily = BebasNeue, color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(25.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = VermelhoFundoLogin,
            unfocusedContainerColor = VermelhoFundoLogin,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
}

@Composable
private fun CartaoDependente(
    dependente: Dependente,
    onEditar: () -> Unit,
    onRemover: () -> Unit
) {
    var mostrarConfirmacao by remember { mutableStateOf(false) }

    if (mostrarConfirmacao) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacao = false },
            containerColor = FundoEscuro,
            title = { Text("Remover dependente?", fontFamily = BebasNeue, color = Color.White) },
            text = {
                Text(
                    "Deseja remover ${dependente.nome} da sua lista de dependentes?",
                    fontFamily = BebasNeue,
                    color = Color.White,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { mostrarConfirmacao = false; onRemover() },
                    colors = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Remover", fontFamily = BebasNeue, color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacao = false }) {
                    Text("Cancelar", fontFamily = BebasNeue, color = Color.White)
                }
            }
        )
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VermelhoFundoLogin),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ChildCare,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = dependente.nome.ifBlank { "Sem nome" },
                        fontFamily = BebasNeue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Row {
                    IconButton(onClick = onEditar, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Black, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = { mostrarConfirmacao = true }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Remover", tint = Color.Black, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            HorizontalDivider(color = Color.Black.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(6.dp))

            InfoDependente("Nascimento", dependente.dataNascimento)
            InfoDependente("CPF", dependente.cpf.formatarCpf())
            InfoDependente("Parentesco", dependente.grauParentesco)
        }
    }
}

@Composable
private fun InfoDependente(label: String, valor: String) {
    Row(modifier = Modifier.padding(vertical = 1.dp)) {
        Text(
            text = "$label: ",
            fontFamily = BebasNeue,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = valor.ifBlank { "—" },
            fontFamily = BebasNeue,
            fontSize = 13.sp,
            color = Color.Black
        )
    }
}


private fun String.formatarCpf(): String {
    val digits = filter { it.isDigit() }.take(11)
    return buildString {
        digits.forEachIndexed { i, c ->
            if (i == 3 || i == 6) append('.')
            if (i == 9) append('-')
            append(c)
        }
    }
}

private fun String.formatarTelefone(): String {
    val digits = filter { it.isDigit() }.take(11)
    return buildString {
        digits.forEachIndexed { i, c ->
            if (i == 0) append('(')
            if (i == 2) append(") ")
            if ((digits.length == 11 && i == 7) || (digits.length == 10 && i == 6)) append('-')
            append(c)
        }
    }
}

private fun formatarData(novo: String, atual: String): String {
    val digits = novo.filter { it.isDigit() }.take(8)
    return buildString {
        digits.forEachIndexed { i, c ->
            if (i == 2 || i == 4) append('/')
            append(c)
        }
    }
}