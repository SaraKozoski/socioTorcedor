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
}
