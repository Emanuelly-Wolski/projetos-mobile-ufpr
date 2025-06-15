package com.example.harrypotterapi

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapi.api.RetrofitInstance
import com.example.harrypotterapi.databinding.ActivityListarProfessorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListarProfessor : AppCompatActivity() {
    private lateinit var binding: ActivityListarProfessorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarProfessorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val professores = RetrofitInstance.api.getProfessores()

                // Pega os nomes dos professores
                val nomesProfessores = professores.map { it.name }

                // Cria o adaptador do AutoComplete
                val adapter = ArrayAdapter(
                    this@ListarProfessor,
                    android.R.layout.simple_dropdown_item_1line,
                    nomesProfessores
                )
                binding.inputNomeProfessor.setAdapter(adapter)

                // 🔥 Quando clicar no botão, busca pelo nome selecionado
                binding.btnBuscarProfessor.setOnClickListener {
                    val nomeSelecionado = binding.inputNomeProfessor.text.toString()

                    val professor = professores.find { it.name.equals(nomeSelecionado, ignoreCase = true) }

                    professor?.let {
                        binding.txtResultadoProfessor.text = """
                            Nome: ${it.name}
                            Nomes Alternativos: ${it.alternate_names?.joinToString(", ") ?: "Nenhum"}
                            Espécie: ${it.species}
                            Casa: ${it.house ?: "Sem casa"}
                        """.trimIndent()
                    } ?: run {
                        binding.txtResultadoProfessor.text = "Professor(a) não encontrado."
                    }
                }

            } catch (e: Exception) {
                binding.txtResultadoProfessor.text = "Erro: ${e.message}"
            }
        }
    }
}
