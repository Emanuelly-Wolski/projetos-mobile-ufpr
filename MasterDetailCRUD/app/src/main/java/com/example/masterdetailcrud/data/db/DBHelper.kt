package com.example.masterdetailcrud.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            const val DATABASE_NAME = "filmes.db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "filmes"
        }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                genero TEXT NOT NULL,
                ano INTEGER NOT NULL,
                diretor TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)

        db.execSQL("INSERT INTO $TABLE_NAME (titulo, diretor, ano, genero) VALUES ('Interestelar', 'Christopher Nolan', 2014, 'Ficção Científica')")
        db.execSQL("INSERT INTO $TABLE_NAME (titulo, diretor, ano, genero) VALUES ('Senhor dos Anéis', 'Peter Jackson', 2001, 'Fantasia')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}