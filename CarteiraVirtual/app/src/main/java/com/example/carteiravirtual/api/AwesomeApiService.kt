package com.example.carteiravirtual.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeApiService {
    @GET("json/last/{pair}")
    fun getCotacao(@Path("pair") pair: String): Call<Map<String, CotacaoResponse>>

    data class CotacaoResponse(
        val bid: String
    )
}