package com.example.carteiravirtual.api

import java.text.DecimalFormat

object FormatUtil {
    fun formatMoney(valor: Double): String {
        return DecimalFormat("#,##0.00").format(valor)
    }

    fun formatBTC(valor: Double): String {
        return DecimalFormat("#,##0.0000").format(valor)
    }

    fun format(moeda: String, valor: Double): String {
        return when (moeda) {
            "BTC" -> formatBTC(valor)
            else -> formatMoney(valor)
        }
    }
}