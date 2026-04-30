package com.wideias.sociotorcedor.ui.planos

import androidx.compose.ui.graphics.Color
import com.wideias.sociotorcedor.ui.home.HomeColors

enum class TipoPlano { RED, GOLD, BLACK, NENHUM }

data class PlanoInfo(
    val tipo: TipoPlano,
    val nome: String,
    val cor: Color,
    val preco: String,
    val parcelas: String,
    val especificacoes: String,
    val diferenciais: List<Pair<String, String>>
)

val planosInfo = listOf(
    PlanoInfo(
        tipo = TipoPlano.RED,
        nome = "RED",
        cor = HomeColors.FundoCards1,
        preco = "119,99",
        parcelas = "12X DE R$ 69,90",
        especificacoes = "Tenha direito a dois ingressos para todos os jogos do Soccer Club, acompanhando o time de perto em todas as competições, sem preocupação; Aproveite descontos e vantagens exclusivas em comércios parceiros da região, como restaurantes, lojas, serviços e muito mais; Receba um copo personalizado e exclusivo, feito especialmente para quem faz parte da nossa torcida oficial; Participe de ações especiais, sorteios, promoções e eventos voltados apenas para sócios torcedores",
        diferenciais = listOf(
            "Ganhe 2 copos Exclusivos" to "Entrada exclusiva no WD",
            "Expêriencias exclusivas" to "Descontos de 5% em produtos da loja",
            "Camiseta Oficial" to "1 ingresso no setor de area coberta"
        )
    ),
    PlanoInfo(
        tipo = TipoPlano.GOLD,
        nome = "GOLD",
        cor = HomeColors.Cards2,
        preco = "229,99",
        parcelas = "12X DE R$ 119,90",
        especificacoes = "Tenha direito a quatro ingressos para todos os jogos do Soccer Club, acompanhando o time de perto em todas as competições, sem preocupação; Aproveite descontos e vantagens exclusivas em comércios parceiros da região; Receba um copo personalizado e exclusivo; Participe de ações especiais, sorteios, promoções e eventos voltados apenas para sócios torcedores",
        diferenciais = listOf(
            "Ganhe 4 copos Exclusivos" to "Entrada exclusiva no WD",
            "Expêriencias exclusivas" to "Descontos de 10% em produtos da loja",
            "Camiseta Oficial" to "2 ingressos no setor de area coberta"
        )
    ),
    PlanoInfo(
        tipo = TipoPlano.BLACK,
        nome = "BLACK",
        cor = HomeColors.Card3ComTexto,
        preco = "339,99",
        parcelas = "12X DE R$ 169,90",
        especificacoes = "Tenha direito a seis ingressos VIP para todos os jogos do Soccer Club, com acesso ao camarote exclusivo; Aproveite todos os benefícios RED e GOLD mais acesso ao lounge VIP; Receba kit exclusivo com camiseta, cachecol e copo personalizado; Participe de meet & greet com jogadores do elenco",
        diferenciais = listOf(
            "Kit VIP Exclusivo" to "Acesso ao Camarote VIP",
            "Meet & Greet jogadores" to "Descontos de 20% na loja",
            "6 Ingressos por jogo" to "Estacionamento reservado"
        )
    )
)