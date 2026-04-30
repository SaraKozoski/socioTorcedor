package com.wideias.sociotorcedor.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class Atalho(
    val rota: String,
    val label: String,
    val icone: ImageVector
)

val todasTelas = listOf(
    Atalho("lanchonete", "Lanchonete", Icons.Default.RestaurantMenu),
    Atalho("time",       "Time",       Icons.Default.SportsSoccer),
    Atalho("planos",     "Planos",     Icons.Default.CardMembership),
    Atalho("ingresso",   "Ingressos",  Icons.Default.ConfirmationNumber),
    Atalho("credito",    "Crédito",    Icons.Default.AccountBalanceWallet)
)

object AtalhoManager {
    private const val PREFS = "atalhos_prefs"
    private const val KEY = "historico_rotas"
    private const val MAX = 20

    fun registrarAcesso(context: Context, rota: String) {
        if (todasTelas.none { it.rota == rota }) return
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val historico = getHistorico(prefs).toMutableList()
        historico.add(0, rota)
        val salvar = historico.take(MAX).joinToString(",")
        prefs.edit().putString(KEY, salvar).apply()
    }

    fun getAtalhosOrdenados(context: Context): List<Atalho> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val historico = getHistorico(prefs)
        val contagem = historico.groupingBy { it }.eachCount()
        return todasTelas.sortedByDescending { contagem[it.rota] ?: 0 }
    }

    private fun getHistorico(prefs: SharedPreferences): List<String> {
        val raw = prefs.getString(KEY, "") ?: ""
        return if (raw.isBlank()) emptyList() else raw.split(",")
    }
}
