package com.example.masterdetailcrud.controller

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterdetailcrud.R
import com.example.masterdetailcrud.data.dao.FilmeDao
import com.example.masterdetailcrud.model.Filme

class QueryActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView

    private lateinit var filmeDao: FilmeDao
    private lateinit var termo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        listView = findViewById(R.id.lvResultados)
        emptyTextView = findViewById(R.id.tvEmptyQuery)

        filmeDao = FilmeDao(this)

        termo = intent.getStringExtra("searchTerm") ?: ""
        buscarFilmes()
    }

    private fun buscarFilmes() {
        val resultados = filmeDao.searchByTitulo(termo)

        if (resultados.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
            emptyTextView.text = "Nenhum filme encontrado com \"$termo\""
        } else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultados)
            listView.adapter = adapter
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedFilme = parent.getItemAtPosition(position) as Filme
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("filmeId", selectedFilme.id)
            }
            startActivity(intent)
        }
    }
}
