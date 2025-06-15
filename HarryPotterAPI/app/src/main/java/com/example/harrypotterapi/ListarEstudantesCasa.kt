package com.example.harrypotterapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapi.api.RetrofitInstance
import com.example.harrypotterapi.databinding.ActivityListarEstudantesCasaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListarEstudantesCasa : AppCompatActivity() {
    private lateinit var binding: ActivityListarEstudantesCasaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarEstudantesCasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscarEstudantes.setOnClickListener {
            val casa = when {
                binding.radioGryffindor.isChecked -> "gryffindor"
                binding.radioHufflepuff.isChecked -> "hufflepuff"
                binding.radioRavenclaw.isChecked -> "ravenclaw"
                binding.radioSlytherin.isChecked -> "slytherin"
                else -> ""
            }

            if (casa.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val estudantes = RetrofitInstance.api.getEstudantesPorCasa(casa)
                        binding.txtResultadoEstudantes.text = estudantes.joinToString("\n") { it.name }
                    } catch (e: Exception) {
                        binding.txtResultadoEstudantes.text = "Erro: ${e.message}"
                    }
                }
            } else {
                binding.txtResultadoEstudantes.text = "Selecione uma casa."
            }
        }
    }
}
