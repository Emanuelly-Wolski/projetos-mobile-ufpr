package com.example.harrypotterapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapi.api.RetrofitInstance
import com.example.harrypotterapi.databinding.ActivityListarPersonagemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListarPersonagem : AppCompatActivity() {
    private lateinit var binding: ActivityListarPersonagemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarPersonagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscarPersonagem.setOnClickListener {
            val idText = binding.inputId.text.toString()

            if (idText.isEmpty()) {
                Toast.makeText(this, "Informe um ID (números inteiros)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = idText.toIntOrNull()
            if (id == null) {
                Toast.makeText(this, "Digite um número válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val personagens = RetrofitInstance.api.getCharacters()

                    if (id < 0 || id >= personagens.size) {
                        binding.txtResultado.text = "ID inválido. Digite entre 0 e ${personagens.size - 1}"
                        return@launch
                    }

                    val personagem = personagens[id]

                    binding.txtResultado.text = """
                        Nome: ${personagem.name}
                        Espécie: ${personagem.species ?: "Não informado"}
                        Casa: ${personagem.house ?: "Sem casa"}
                    """.trimIndent()

                    Picasso.get().load(personagem.image).into(binding.imgPersonagem)

                } catch (e: Exception) {
                    binding.txtResultado.text = "Erro: ${e.message}"
                }
            }
        }
    }
}

