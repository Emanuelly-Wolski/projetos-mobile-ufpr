package com.example.masterdetailcrud.data.dao

import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.masterdetailcrud.data.db.DBHelper
import com.example.masterdetailcrud.model.Filme

class FilmeDao (private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertFilme (filme: Filme): Long {
        val db = dbHelper.writableDatabase
        val values = contentValuesOf().apply {
            put("titulo", filme.titulo)
            put("genero", filme.genero)
            put("ano", filme.ano)
            put("diretor", filme.diretor)
        }

        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllFilmes(): List<Filme> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null)
        val filmes = mutableListOf<Filme>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow("ano"))
            val diretor = cursor.getString(cursor.getColumnIndexOrThrow("diretor"))
            filmes.add(Filme(id, titulo, genero, ano, diretor))
        }
        cursor.close()
        db.close()
        return filmes
    }

    fun getFilmeById(id: Int): Filme? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBHelper.TABLE_NAME, null, "id = ?", arrayOf(id.toString()), null, null, null)
        var filme: Filme? = null
        if (cursor.moveToFirst()) {
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow("ano"))
            val diretor = cursor.getString(cursor.getColumnIndexOrThrow("diretor"))
            filme = Filme(id, titulo, genero, ano, diretor)
        }
        cursor.close()
        db.close()
        return filme
    }

    fun updateFilme(filme: Filme): Int {
        val db = dbHelper.writableDatabase
        val values = contentValuesOf().apply {
            put("titulo", filme.titulo)
            put("genero", filme.genero)
            put("ano", filme.ano)
            put("diretor", filme.diretor)
        }
        val rowsAffected =
            db.update(DBHelper.TABLE_NAME, values, "id = ?", arrayOf(filme.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteFilme(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete(DBHelper.TABLE_NAME, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun searchByTitulo(titulo: String): List<Filme> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBHelper.TABLE_NAME, null, "titulo LIKE ?", arrayOf("%$titulo%"), null, null, null)
        val filmes = mutableListOf<Filme>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow("genero"))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow("ano"))
            val diretor = cursor.getString(cursor.getColumnIndexOrThrow("diretor"))
            filmes.add(Filme(id, titulo, genero, ano, diretor))
        }
        cursor.close()
        db.close()
        return filmes
    }


}