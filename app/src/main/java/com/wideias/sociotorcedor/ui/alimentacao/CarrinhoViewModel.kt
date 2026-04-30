package com.wideias.sociotorcedor.ui.alimentacao

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarrinhoViewModel : ViewModel() {

    private val _itens = MutableStateFlow<List<ItemCarrinho>>(emptyList())
    val itens: StateFlow<List<ItemCarrinho>> = _itens.asStateFlow()

    val precoTotal: Double get() = _itens.value.sumOf { it.precoTotal }

    companion object {
        const val LIMITE_POR_ITEM = 15
    }

    fun quantidadeDoItem(produtoId: String): Int =
        _itens.value.count { it.produto.id == produtoId }

    fun adicionarItem(item: ItemCarrinho): Boolean {
        val lista = _itens.value.toMutableList()
        val quantidade = lista.count { it.produto.id == item.produto.id }
        if (quantidade >= LIMITE_POR_ITEM) return false
        lista.add(item)
        _itens.value = lista
        return true
    }

    fun removerUm(produtoId: String) {
        val lista = _itens.value.toMutableList()
        val index = lista.indexOfLast { it.produto.id == produtoId }
        if (index >= 0) lista.removeAt(index)
        _itens.value = lista
    }

    fun removerTodos(produtoId: String) {
        _itens.value = _itens.value.filter { it.produto.id != produtoId }
    }

    fun limparCarrinho() {
        _itens.value = emptyList()
    }
}
