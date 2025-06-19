package com.example.finapp

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.DBHelper
import com.example.finapp.R
import com.example.finapp.Transacao
import com.example.finapp.adapter.TransacaoAdapter

class ExtratoActivity : AppCompatActivity() {

    private lateinit var rgFiltro: RadioGroup
    private lateinit var rbTodos: RadioButton
    private lateinit var rbDebitos: RadioButton
    private lateinit var rbCreditos: RadioButton
    private lateinit var recyclerExtrato: RecyclerView
    private lateinit var txtSaldo: TextView

    private lateinit var listaCompleta: List<Transacao>
    private lateinit var adapter: TransacaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)

        rgFiltro = findViewById(R.id.rgFiltro)
        rbTodos = findViewById(R.id.rbTodos)
        rbDebitos = findViewById(R.id.rbDebitos)
        rbCreditos = findViewById(R.id.rbCreditos)
        recyclerExtrato = findViewById(R.id.recyclerExtrato)
        txtSaldo = findViewById(R.id.txtSaldo)

        recyclerExtrato.layoutManager = LinearLayoutManager(this)

        carregarDados()

        rgFiltro.setOnCheckedChangeListener { _, _ ->
            aplicarFiltro()
        }
    }

    private fun carregarDados() {
        val db = DBHelper(this)
        listaCompleta = db.listarOperacoes()
        aplicarFiltro()
    }

    private fun aplicarFiltro() {
        val listaFiltrada = when {
            rbDebitos.isChecked -> listaCompleta.filter { it.tipo == "DEBITO" }
            rbCreditos.isChecked -> listaCompleta.filter { it.tipo == "CREDITO" }
            else -> listaCompleta
        }

        adapter = TransacaoAdapter(listaFiltrada)
        recyclerExtrato.adapter = adapter

        atualizarSaldo(listaCompleta)
    }

    private fun atualizarSaldo(lista: List<Transacao>) {
        var saldo = 0.0
        for (op in lista) {
            if (op.tipo == "CREDITO") {
                saldo += op.valor
            } else if (op.tipo == "DEBITO") {
                saldo -= op.valor
            }
        }
        val cor = if (saldo < 0) android.graphics.Color.RED else android.graphics.Color.rgb(0, 128, 0)
        txtSaldo.text = "Saldo: R$ %.2f".format(saldo)
        txtSaldo.setTextColor(cor)
    }
}
