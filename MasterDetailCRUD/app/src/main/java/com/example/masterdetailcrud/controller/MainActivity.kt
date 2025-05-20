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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterdetailcrud.R
import com.example.masterdetailcrud.data.dao.FilmeDao
import com.example.masterdetailcrud.model.Filme
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var filmeDao: FilmeDao
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvFilmes)
        emptyTextView = findViewById(R.id.tvEmpty)
        filmeDao = FilmeDao(this)
        listAllFilmes()

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedFilme = parent.getItemAtPosition(position) as Filme
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("filmeId", selectedFilme.id)
            }
            startActivity(intent)
        }

    }

    private fun listAllFilmes() {
        val filmes = filmeDao.getAllFilmes()
        if (filmes.isEmpty()) {
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE

        } else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
            val adapter: ArrayAdapter<Filme> = ArrayAdapter(this, android.R.layout.simple_list_item_1, filmes)
            listView.adapter = adapter
        }
    }

    fun newFilme(view: View) {
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    fun searchByTitulo(view: View) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Pesquisar Filme")

        val input = android.widget.EditText(this)
        input.hint = "Digite o título do filme"
        builder.setView(input)

        builder.setPositiveButton("Buscar") { dialog, _ ->
            val termo = input.text.toString()
            val intent = Intent(this, QueryActivity::class.java).apply {
                putExtra("searchTerm", termo)
            }
            startActivity(intent)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    override fun onResume() {
        super.onResume()
        listAllFilmes()
    }
}
