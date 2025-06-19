package com.example.finapp

data class Transacao(
    val id: Int = 0,
    val tipo: String,
    val descricao: String,
    val valor: Double
)
