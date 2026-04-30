package com.wideias.sociotorcedor.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wideias.sociotorcedor.data.local.entity.SocioEntity
import com.wideias.sociotorcedor.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val MOCK_SOCIO_COM_PLANO = SocioEntity(
    id = "1",
    nome = "João Torcedor",
    cpf = "12345678901",
    email = "joao@teste.com",
    plano = "Plano Ouro",
    numeroCadastro = "0001",
    fotoUrl = null
)

private val MOCK_SOCIO_SEM_PLANO = SocioEntity(
    id = "2",
    nome = "Maria Torcedora",
    cpf = "98765432100",
    email = "maria@teste.com",
    plano = "",
    numeroCadastro = "0002",
    fotoUrl = null
)

private val MOCK_USUARIOS = mapOf(
    "12345678901" to Pair("123456", MOCK_SOCIO_COM_PLANO),
    "98765432100" to Pair("123456", MOCK_SOCIO_SEM_PLANO)
)

class LoginViewModel(
    private val userViewModel: UserViewModel
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun loginComCpf(cpf: String, senha: String) {
        if (cpf.length != 11) {
            _loginState.value = LoginState.Erro("CPF inválido")
            return
        }
        if (senha.length < 6) {
            _loginState.value = LoginState.Erro("Senha deve ter pelo menos 6 caracteres")
            return
        }

        _loginState.value = LoginState.Carregando
        viewModelScope.launch {
            delay(800)
            val usuario = MOCK_USUARIOS[cpf]
            if (usuario != null && usuario.first == senha) {
                userViewModel.login(usuario.second)
                _loginState.value = LoginState.Sucesso
            } else {
                _loginState.value = LoginState.Erro("CPF ou senha incorretos")
            }
        }
    }

    fun loginComGoogle(idToken: String) {
        _loginState.value = LoginState.Carregando
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Sucesso
            } catch (e: Exception) {
                _loginState.value = LoginState.Erro("Erro Google: ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

class LoginViewModelFactory(
    private val userViewModel: UserViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userViewModel) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Carregando : LoginState()
    object Sucesso : LoginState()
    data class Erro(val mensagem: String) : LoginState()
}