package com.example.harrypotterapi.model

data class Personagem(
    val name: String,
    val species: String?,
    val house: String?,
    val image: String?,
    val alternate_names: List<String>?
)
