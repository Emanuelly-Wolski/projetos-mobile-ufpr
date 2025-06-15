package com.example.harrypotterapi.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Biblioteca para requisições HTTP/REST
// Cria e mantém uma única instância do Retrofit configurada com a URL da API

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
