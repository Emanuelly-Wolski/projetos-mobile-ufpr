package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonIniciar = findViewById<Button>(R.id.buttonIniciar)
        buttonIniciar.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
}