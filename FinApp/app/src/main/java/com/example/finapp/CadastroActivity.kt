package com.example.finapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.*

class CadastroActivity : AppCompatActivity() {

    private var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val radioTipo = findViewById<RadioGroup>(R.id.radioTipo)
        val editDescricao = findViewById<EditText>(R.id.editDescricao)
        val editValor = findViewById<EditText>(R.id.editValor)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        configurarCampoMonetario(editValor)

        btnSalvar.setOnClickListener {
            val tipo = if (radioTipo.checkedRadioButtonId == R.id.radioCredito) "CREDITO" else "DEBITO"
            val descricao = editDescricao.text.toString()

            // Converte R$ 1.234,56 -> 1234.56
            val valorStr = editValor.text.toString()
                .replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim()
            val valor = valorStr.toDoubleOrNull()

            if (descricao.isBlank() || valor == null) {
                Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = DBHelper(this)

            // Verificar saldo antes de aceitar débito
            if (tipo == "DEBITO") {
                val transacoes = db.listarOperacoes()
                val saldo = transacoes.sumOf {
                    if (it.tipo == "CREDITO") it.valor else -it.valor
                }

                if (valor > saldo) {
                    Toast.makeText(this, "Saldo insuficiente.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val transacao = Transacao(tipo = tipo, descricao = descricao, valor = valor)
            db.inserirOperacao(transacao)

            Toast.makeText(this, "Transação salva com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun configurarCampoMonetario(editText: EditText) {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                try {
                    val str = s.toString()
                        .replace("[R$,.\\s]".toRegex(), "")
                    val valor = str.toDouble() / 100

                    editText.setText(formatador.format(valor))
                    editText.setSelection(editText.text.length)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                isUpdating = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
