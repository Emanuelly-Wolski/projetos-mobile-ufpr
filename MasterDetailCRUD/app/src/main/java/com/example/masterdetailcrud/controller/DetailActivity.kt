package com.example.masterdetailcrud.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterdetailcrud.R
import com.example.masterdetailcrud.data.dao.FilmeDao
import com.example.masterdetailcrud.model.Filme

class DetailActivity : AppCompatActivity() {

    private lateinit var edtTitulo: EditText
    private lateinit var edtDiretor: EditText
    private lateinit var edtAno: EditText
    private lateinit var edtGenero: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnExcluir: Button

    private lateinit var filmeDao: FilmeDao
    private var filmeId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        edtTitulo = findViewById(R.id.edtTitulo)
        edtDiretor = findViewById(R.id.edtDiretor)
        edtAno = findViewById(R.id.edtAno)
        edtGenero = findViewById(R.id.edtGenero)
        btnSalvar = findViewById(R.id.btnSalvar)
        btnExcluir = findViewById(R.id.btnExcluir)

        filmeDao = FilmeDao(this)

        filmeId = intent.getIntExtra("filmeId", -1).takeIf { it != -1 }

        if (filmeId != null) {
            carregarFilme(filmeId!!)
        } else {
            btnExcluir.visibility = View.GONE
        }

        btnSalvar.setOnClickListener {
            salvarFilme()
        }

        btnExcluir.setOnClickListener {
            excluirFilme()
        }
    }

    private fun carregarFilme(id: Int) {
        val filme = filmeDao.getFilmeById(id)
        filme?.let {
            edtTitulo.setText(it.titulo)
            edtDiretor.setText(it.diretor)
            edtAno.setText(it.ano.toString())
            edtGenero.setText(it.genero)
        }
    }

    private fun salvarFilme() {
        val titulo = edtTitulo.text.toString().trim()
        val diretor = edtDiretor.text.toString().trim()
        val ano = edtAno.text.toString().toIntOrNull()
        val genero = edtGenero.text.toString().trim()

        if (titulo.isEmpty() || diretor.isEmpty() || ano == null) {
            Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show()
            return
        }

        val filme = Filme(
            id = filmeId ?: 0,
            titulo = titulo,
            diretor = diretor,
            ano = ano,
            genero = genero
        )

        if (filmeId == null) {
            filmeDao.insertFilme(filme)
        } else {
            filmeDao.updateFilme(filme)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun excluirFilme() {
        filmeId?.let {
            filmeDao.deleteFilme(it)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}

