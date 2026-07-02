package com.wideias.sociotorcedor.ui.meueespaco

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wideias.sociotorcedor.viewmodel.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MeuEspacoState {
    object Carregando : MeuEspacoState()
    data class Sucesso(
        val ingressos: List<Ingresso>,
        val beneficios: List<Beneficio>
    ) : MeuEspacoState()
    data class Erro(val mensagem: String) : MeuEspacoState()
}

class MeuEspacoViewModel(
    private val userViewModel: UserViewModel
) : ViewModel() {

    private val _state = MutableStateFlow<MeuEspacoState>(MeuEspacoState.Carregando)
    val state: StateFlow<MeuEspacoState> = _state

    init {
        carregarDados()
    }

    private fun carregarDados() {
        viewModelScope.launch {
            try {
                val socioId = userViewModel.usuario.value?.id ?: return@launch

                // TODO: substituir por chamada real ao repositório
                val ingressosMock = listOf(
                    Ingresso(
                        id = "ING001",
                        jogo = "Athletico PR x Flamengo",
                        adversario = "Flamengo",
                        data = "25/05/2026",
                        hora = "19:00",
                        setor = "Setor Leste",
                        assento = "Fila 12 - Cadeira 34",
                        confirmado = true,
                        qrCodeData = "SOCIO:$socioId|JOGO:ING001|SETOR:LESTE|ASSENTO:12-34"
                    ),
                    Ingresso(
                        id = "ING002",
                        jogo = "Athletico PR x Palmeiras",
                        adversario = "Palmeiras",
                        data = "01/06/2026",
                        hora = "16:00",
                        setor = "Setor Norte",
                        assento = "Fila 5 - Cadeira 10",
                        confirmado = false,
                        qrCodeData = "SOCIO:$socioId|JOGO:ING002|SETOR:NORTE|ASSENTO:05-10"
                    )
                )

                val beneficiosMock = listOf(
                    Beneficio(
                        id = "BEN001",
                        titulo = "Camisa Oficial",
                        descricao = "Desconto na camisa oficial do Athletico na loja parceira",
                        desconto = "20% OFF",
                        codigo = "SOCIO20CAM",
                        validade = "30/06/2026"
                    ),
                    Beneficio(
                        id = "BEN002",
                        titulo = "Produtos de Linha",
                        descricao = "Desconto em produtos selecionados da loja oficial",
                        desconto = "15% OFF",
                        codigo = "SOCIO15PRO",
                        validade = "31/05/2026"
                    ),
                    Beneficio(
                        id = "BEN003",
                        titulo = "Acessórios",
                        descricao = "Canecas, bonés e acessórios com desconto exclusivo",
                        desconto = "10% OFF",
                        codigo = "SOCIO10ACE",
                        validade = "31/12/2026"
                    )
                )

                _state.value = MeuEspacoState.Sucesso(
                    ingressos = ingressosMock,
                    beneficios = beneficiosMock
                )
            } catch (e: Exception) {
                _state.value = MeuEspacoState.Erro("Erro ao carregar dados: ${e.message}")
            }
        }
    }

    fun recarregar() {
        _state.value = MeuEspacoState.Carregando
        carregarDados()
    }
}

class MeuEspacoViewModelFactory(
    private val userViewModel: UserViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeuEspacoViewModel::class.java)) {
            return MeuEspacoViewModel(userViewModel) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}