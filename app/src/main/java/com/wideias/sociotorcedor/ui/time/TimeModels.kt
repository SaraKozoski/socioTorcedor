package com.wideias.sociotorcedor.ui.time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wideias.sociotorcedor.data.remote.ApiClassificacao
import com.wideias.sociotorcedor.data.remote.ApiJogador
import com.wideias.sociotorcedor.data.remote.ApiPartida
import com.wideias.sociotorcedor.data.repository.TimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Carregando : UiState<Nothing>()
    data class Sucesso<T>(val dados: T) : UiState<T>()
    data class Erro(val mensagem: String) : UiState<Nothing>()
}

class TimeViewModel : ViewModel() {

    private val repository = TimeRepository()

    private val _partidas = MutableStateFlow<UiState<List<ApiPartida>>>(UiState.Carregando)
    val partidas: StateFlow<UiState<List<ApiPartida>>> = _partidas

    private val _elenco = MutableStateFlow<UiState<List<ApiJogador>>>(UiState.Carregando)
    val elenco: StateFlow<UiState<List<ApiJogador>>> = _elenco

    private val _classificacao = MutableStateFlow<UiState<List<ApiClassificacao>>>(UiState.Carregando)
    val classificacao: StateFlow<UiState<List<ApiClassificacao>>> = _classificacao

    init {
        carregarPartidas()
        carregarElenco()
        carregarClassificacao()
    }

    fun carregarPartidas() {
        viewModelScope.launch {
            _partidas.value = UiState.Carregando
            repository.getPartidas().fold(
                onSuccess = { _partidas.value = UiState.Sucesso(it) },
                onFailure = { _partidas.value = UiState.Erro(it.message ?: "Erro desconhecido") }
            )
        }
    }

    fun carregarElenco() {
        viewModelScope.launch {
            _elenco.value = UiState.Carregando
            repository.getElenco().fold(
                onSuccess = { _elenco.value = UiState.Sucesso(it) },
                onFailure = { _elenco.value = UiState.Erro(it.message ?: "Erro desconhecido") }
            )
        }
    }

    fun carregarClassificacao() {
        viewModelScope.launch {
            _classificacao.value = UiState.Carregando
            repository.getClassificacao().fold(
                onSuccess = { _classificacao.value = UiState.Sucesso(it) },
                onFailure = { _classificacao.value = UiState.Erro(it.message ?: "Erro desconhecido") }
            )
        }
    }
}