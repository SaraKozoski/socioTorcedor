package com.wideias.sociotorcedor.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

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
            try {
                _loginState.value = LoginState.Sucesso
            } catch (e: Exception) {
                _loginState.value = LoginState.Erro("Erro: ${e.message}")
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

sealed class LoginState {
    object Idle : LoginState()
    object Carregando : LoginState()
    object Sucesso : LoginState()
    data class Erro(val mensagem: String) : LoginState()
}