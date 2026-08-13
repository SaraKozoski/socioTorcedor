package com.wideias.sociotorcedor.ui.meueespaco

import java.time.LocalDateTime

data class Ingresso(
    val id: String,
    val jogo: String,
    val adversario: String,
    val data: String,
    val hora: String,
    val setor: String,
    val assento: String,
    val confirmado: Boolean,
    val qrCodeData: String  // string codificada no QR (ex: id do socio + id do jogo)
)

data class Beneficio(
    val id: String,
    val titulo: String,
    val descricao: String,
    val desconto: String,      // ex: "15% OFF"
    val codigo: String,        // código a usar na loja
    val validade: String,
    val usado: Boolean = false
)