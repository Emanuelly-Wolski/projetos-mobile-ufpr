package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("score", 0)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)
        val buttonReiniciar = findViewById<Button>(R.id.buttonReiniciar)

        textViewResultado.text = "Sua nota final foi $score de 100."

        buttonReiniciar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}