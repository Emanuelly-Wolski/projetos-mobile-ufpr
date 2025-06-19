package com.example.finapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "finapp.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE operacoes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tipo TEXT NOT NULL,
                descricao TEXT NOT NULL,
                valor REAL NOT NULL
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS operacoes")
        onCreate(db)
    }

    fun inserirOperacao(operacao: Transacao) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("tipo", operacao.tipo)
            put("descricao", operacao.descricao)
            put("valor", operacao.valor)
        }
        db.insert("operacoes", null, cv)
        db.close()
    }

    fun listarOperacoes(): List<Transacao> {
        val lista = mutableListOf<Transacao>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM operacoes ORDER BY id DESC", null)
        while (cursor.moveToNext()) {
            val op = Transacao(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo")),
                descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao")),
                valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            )
            lista.add(op)
        }
        cursor.close()
        db.close()
        return lista
    }
}