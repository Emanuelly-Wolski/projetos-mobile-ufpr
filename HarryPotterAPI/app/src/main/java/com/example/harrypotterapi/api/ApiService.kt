package com.example.harrypotterapi.api

import com.example.harrypotterapi.model.Feitico
import com.example.harrypotterapi.model.Personagem
import retrofit2.http.GET
import retrofit2.http.Path

//  Endpoints da API
interface ApiService {

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): List<Personagem>

    @GET("api/characters/staff")
    suspend fun getProfessores(): List<Personagem>

    @GET("api/characters/house/{house}")
    suspend fun getEstudantesPorCasa(@Path("house") house: String): List<Personagem>

    @GET("api/spells")
    suspend fun getFeiticos(): List<Feitico>

    @GET("api/characters")
    suspend fun getCharacters(): List<Personagem>
}