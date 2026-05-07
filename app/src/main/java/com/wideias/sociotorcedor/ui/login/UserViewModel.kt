package com.wideias.sociotorcedor.viewmodel

import androidx.lifecycle.ViewModel
import com.wideias.sociotorcedor.data.local.entity.SocioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {

    private val _usuario = MutableStateFlow<SocioEntity?>(null)
    val usuario: StateFlow<SocioEntity?> = _usuario.asStateFlow()

    private val _pontos = MutableStateFlow(0)
    val pontos: StateFlow<Int> = _pontos.asStateFlow()

    private val _saldoNumerico = MutableStateFlow(0.0)
    val saldoNumerico: StateFlow<Double> = _saldoNumerico.asStateFlow()

    private val _saldo = MutableStateFlow("R\$0,00")
    val saldo: StateFlow<String> = _saldo.asStateFlow()

    val estaLogado: Boolean get() = _usuario.value != null
    val temPlano: Boolean get() = _usuario.value?.plano?.isNotBlank() == true

    fun login(socio: SocioEntity) {
        _usuario.value = socio
    }

    fun logout() {
        _usuario.value = null
        _pontos.value = 0
    }

    fun adicionarPontos(reais: Double) {
        _pontos.value += reais.toInt()
    }

    fun adicionarSaldo(valor: Double) {
        atualizarSaldo(_saldoNumerico.value + valor)
    }

    fun debitarSaldo(valor: Double) {
        val novoSaldo = (_saldoNumerico.value - valor).coerceAtLeast(0.0)
        atualizarSaldo(novoSaldo)
    }

    private fun atualizarSaldo(novoValor: Double) {
        _saldoNumerico.value = novoValor
        val inteiro  = novoValor.toLong()
        val centavos = Math.round((novoValor - inteiro) * 100)
        _saldo.value = "R\$%d,%02d".format(inteiro, centavos)
    }
}