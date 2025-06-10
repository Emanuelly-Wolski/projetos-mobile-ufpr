package com.example.carteiravirtual.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Carteira(
    var real: Double = 100_000.0,
    var dolar: Double = 50_000.0,
    var btc: Double = 0.5
) : Parcelable {

    fun hasSaldo(moeda: String, valor: Double): Boolean {
        return when (moeda) {
            "R$" -> real >= valor
            "$" -> dolar >= valor
            "BTC" -> btc >= valor
            else -> false
        }
    }

    fun converter(origem: String, destino: String, valorOrigem: Double, valorDestino: Double) {
        when (origem) {
            "R$" -> real -= valorOrigem
            "$" -> dolar -= valorOrigem
            "BTC" -> btc -= valorOrigem
        }
        when (destino) {
            "R$" -> real += valorDestino
            "$" -> dolar += valorDestino
            "BTC" -> btc += valorDestino
        }
    }
}
