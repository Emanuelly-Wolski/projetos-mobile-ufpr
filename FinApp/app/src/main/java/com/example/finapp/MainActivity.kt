package com.example.finapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.finapp.CadastroActivity
import com.example.finapp.R
import com.example.finapp.ExtratoActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)
        val btnExtrato = findViewById<Button>(R.id.btnExtrato)
        val btnSair = findViewById<Button>(R.id.btnSair)

        btnCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        btnExtrato.setOnClickListener {
            startActivity(Intent(this, ExtratoActivity::class.java))
        }

        btnSair.setOnClickListener {
            finishAffinity() // Encerra todas as activities e fecha o app
        }
    }
}
