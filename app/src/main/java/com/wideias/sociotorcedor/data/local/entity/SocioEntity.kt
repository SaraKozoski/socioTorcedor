package com.wideias.sociotorcedor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "socios")
data class SocioEntity(
    @PrimaryKey val id: String,
    val nome: String,
    val cpf: String,
    val email: String,
    val plano: String,
    val numeroCadastro: String,
    val fotoUrl: String?
)
