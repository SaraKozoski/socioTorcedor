package com.wideias.sociotorcedor.ui.alimentacao
import com.wideias.sociotorcedor.R

data class ProdutoAlimentacao(
    val id: String,
    val nome: String,
    val preco: Double,
    val descricao: String = "",
    val imagemRes: Int,
    val categoria: String 
)

data class IngredienteAlimentacao(
    val id: Int,
    val nome: String,
    val incluso: Boolean = true
)

data class AdicionalAlimentacao(
    val id: Int,
    val nome: String,
    val preco: Double,
    val selecionado: Boolean = false
)

data class ItemCarrinho(
    val id: String,
    val produto: ProdutoAlimentacao,
    val ingredientesInclusos: List<IngredienteAlimentacao>,
    val adicionaisSelecionados: List<AdicionalAlimentacao>,
    val observacao: String,
    val precoTotal: Double
)

val produtosAlimentacaoMock = listOf(
    ProdutoAlimentacao("1", "Hambúrguer", 24.90, "Smash artesanal com cheddar",    R.drawable.hamburguer, "Lanches"),
    ProdutoAlimentacao("2", "Pizza",      49.90, "Calabresa com borda recheada",   R.drawable.pizza,      "Lanches"),
    ProdutoAlimentacao("3", "Suco",        8.50, "Natural",                        R.drawable.suco,       "Bebidas"),
    ProdutoAlimentacao("4", "Sorvete",    12.00, "Chocolate com pedaços de brownie",R.drawable.sorvete,   "Sobremesas"),
    ProdutoAlimentacao("5", "Agua",        5.00, "Garrafa 500ml",                  R.drawable.agua,       "Bebidas")
)
val ingredientesPadrao = listOf(
    IngredienteAlimentacao(1, "Pão Brioche",    incluso = true),
    IngredienteAlimentacao(2, "Carne 160g",     incluso = true),
    IngredienteAlimentacao(3, "Queijo Cheddar", incluso = true),
    IngredienteAlimentacao(4, "Alface",         incluso = true),
    IngredienteAlimentacao(5, "Tomate",         incluso = true),
    IngredienteAlimentacao(6, "Cebola Roxa",    incluso = true),
    IngredienteAlimentacao(7, "Molho da Casa",  incluso = true)
)

val adicionaisPadrao = listOf(
    AdicionalAlimentacao(1, "Bacon Crocante",    3.00),
    AdicionalAlimentacao(2, "Queijo Extra",      2.50),
    AdicionalAlimentacao(3, "Maionese Caseira",  1.50)
)