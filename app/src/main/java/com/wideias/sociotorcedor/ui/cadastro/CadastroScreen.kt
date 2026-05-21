package com.wideias.sociotorcedor.ui.cadastro

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import com.wideias.sociotorcedor.ui.theme.FundoEscuro
import com.wideias.sociotorcedor.ui.theme.VermelhoBotao
import com.wideias.sociotorcedor.ui.theme.VermelhoFundoLogin
import com.wideias.sociotorcedor.viewmodel.UserViewModel

@Composable
fun CadastroScreen(
    onCadastroSucesso: () -> Unit,
    onLoginClick: () -> Unit,
    userViewModel: UserViewModel,
    viewModel: CadastroViewModel = viewModel(
        factory = CadastroViewModelFactory(userViewModel)
    )
) {
    val cadastroState by viewModel.cadastroState.collectAsState()

    var nome       by remember { mutableStateOf("") }
    var email      by remember { mutableStateOf("") }
    var dataNasc   by remember { mutableStateOf("") }
    var cpf        by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var cep        by remember { mutableStateOf("") }
    var cidade     by remember { mutableStateOf("") }
    var uf         by remember { mutableStateOf("") }
    var numero     by remember { mutableStateOf("") }
    var senha      by remember { mutableStateOf("") }

    LaunchedEffect(cadastroState) {
        if (cadastroState is CadastroState.Sucesso) {
            onCadastroSucesso()
            viewModel.resetState()
        }
    }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor   = VermelhoFundoLogin,
        unfocusedContainerColor = VermelhoFundoLogin,
        focusedTextColor        = Color.Black,
        unfocusedTextColor      = Color.Black,
        focusedBorderColor      = Color.Black,
        unfocusedBorderColor    = Color.Black,
        focusedLabelColor       = Color.White,
        unfocusedLabelColor     = Color.Black,
        cursorColor             = Color.Black
    )
    val fieldShape    = RoundedCornerShape(25.dp)
    val fieldModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
    val fieldTextStyle = TextStyle(fontFamily = BebasNeue, color = Color.Black)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoEscuro)
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = "Logo do Clube",
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Faça seu Cadastro",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = BebasNeue,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome", fontFamily = BebasNeue) },
            placeholder = { Text("Seu nome completo", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontFamily = BebasNeue) },
            placeholder = { Text("exemplo@email.com", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        OutlinedTextField(
            value = dataNasc,
            onValueChange = {
                val digits = it.filter { c -> c.isDigit() }.take(8)
                dataNasc = buildString {
                    digits.forEachIndexed { i, c ->
                        if (i == 2 || i == 4) append('/')
                        append(c)
                    }
                }
            },
            label = { Text("Data de Nascimento", fontFamily = BebasNeue) },
            placeholder = { Text("DD/MM/AAAA", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        OutlinedTextField(
            value = cpf,
            onValueChange = { if (it.length <= 11) cpf = it.filter { c -> c.isDigit() } },
            label = { Text("CPF", fontFamily = BebasNeue) },
            placeholder = { Text("12345678901", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        
        OutlinedTextField(
            value = logradouro,
            onValueChange = { logradouro = it },
            label = { Text("Logradouro", fontFamily = BebasNeue) },
            placeholder = { Text("Rua, Av., etc.", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        OutlinedTextField(
            value = cep,
            onValueChange = { if (it.length <= 8) cep = it.filter { c -> c.isDigit() } },
            label = { Text("CEP", fontFamily = BebasNeue) },
            placeholder = { Text("00000000", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        OutlinedTextField(
            value = cidade,
            onValueChange = { cidade = it },
            label = { Text("Cidade", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uf,
                onValueChange = { if (it.length <= 2) uf = it.uppercase() },
                label = { Text("UF", fontFamily = BebasNeue) },
                placeholder = { Text("SP", fontFamily = BebasNeue) },
                textStyle = fieldTextStyle,
                singleLine = true,
                shape = fieldShape,
                colors = fieldColors,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = numero,
                onValueChange = { numero = it.filter { c -> c.isDigit() } },
                label = { Text("Número", fontFamily = BebasNeue) },
                placeholder = { Text("123", fontFamily = BebasNeue) },
                textStyle = fieldTextStyle,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = fieldShape,
                colors = fieldColors,
                modifier = Modifier.weight(2f)
            )
        }

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha", fontFamily = BebasNeue) },
            textStyle = fieldTextStyle,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            modifier = fieldModifier
        )

        if (cadastroState is CadastroState.Erro) {
            Text(
                text = (cadastroState as CadastroState.Erro).mensagem,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                fontFamily = BebasNeue,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.cadastrar(
                    nome       = nome,
                    email      = email,
                    dataNasc   = dataNasc,
                    cpf        = cpf,
                    logradouro = logradouro,
                    cep        = cep,
                    cidade     = cidade,
                    uf         = uf,
                    numero     = numero,
                    senha      = senha
                )
            },
            enabled = cadastroState !is CadastroState.Carregando,
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = VermelhoBotao),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (cadastroState is CadastroState.Carregando) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    "Cadastrar",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = BebasNeue
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onLoginClick) {
            Text(
                "Já está cadastrado? Faça o seu login aqui",
                color = Color.White,
                fontFamily = BebasNeue
            )
        }
    }
}