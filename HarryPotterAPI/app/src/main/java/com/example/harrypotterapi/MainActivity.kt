package com.example.harrypotterapi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPersonagem.setOnClickListener {
            startActivity(Intent(this, ListarPersonagem::class.java))
        }

        binding.btnProfessor.setOnClickListener {
            startActivity(Intent(this, ListarProfessor::class.java))
        }

        binding.btnEstudantes.setOnClickListener {
            startActivity(Intent(this, ListarEstudantesCasa::class.java))
        }

        binding.btnFeiticos.setOnClickListener {
            startActivity(Intent(this, VerFeiticos::class.java))
        }

        binding.btnSair.setOnClickListener {
            finishAffinity()
        }
    }
}
