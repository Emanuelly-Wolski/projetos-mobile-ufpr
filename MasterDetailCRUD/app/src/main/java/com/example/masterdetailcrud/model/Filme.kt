package com.example.masterdetailcrud.model

data class Filme (val id: Int, val titulo: String, val genero: String, val ano: Int, val diretor: String) {
    override fun toString(): String {
        return "$titulo ($ano)"
    }
}