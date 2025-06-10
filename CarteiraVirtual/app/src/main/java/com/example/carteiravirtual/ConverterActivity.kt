package com.example.carteiravirtual

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carteiravirtual.databinding.ActivityConverterBinding
import com.example.carteiravirtual.api.AwesomeApiService
import com.example.carteiravirtual.api.FormatUtil
import com.example.carteiravirtual.api.RetrofitClient
import com.example.carteiravirtual.model.Carteira
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConverterBinding
    private lateinit var carteira: Carteira
    private val moedas = listOf("R$", "$", "BTC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera a carteira recebida da MainActivity
        carteira = intent.getParcelableExtra<Carteira>("carteira") ?: Carteira()

        // Configura os Spinners com as opções de moeda
        binding.spOrigem.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moedas)
        binding.spDestino.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moedas)

        // Clique no botão Converter
        binding.btnConverter.setOnClickListener { converterMoeda() }
    }

    private fun converterMoeda() {
        val moedaOrigem = moedas[binding.spOrigem.selectedItemPosition]
        val moedaDestino = moedas[binding.spDestino.selectedItemPosition]
        val valorTxt = binding.inputValor.text.toString().replace(',', '.')

        // Moedas não podem ser iguais
        if (moedaOrigem == moedaDestino) {
            showMsg("Escolha moedas diferentes.")
            return
        }
        // Valor inserido deve ser válido e positivo
        val valor = valorTxt.toDoubleOrNull()
        if (valor == null || valor <= 0.0) {
            showMsg("Informe um valor válido.")
            return
        }
        // Testa se há saldo suficiente antes de fazer a request
        if (!carteira.hasSaldo(moedaOrigem, valor)) {
            showMsg("Saldo insuficiente para conversão.")
            return
        }

        // Mostra ProgressBar durante a requisição
        binding.progressBar.visibility = View.VISIBLE
        binding.btnConverter.isEnabled = false
        binding.txtResultado.text = ""

        // Prepara nomes da moeda para a AwesomeAPI
        val origem = when (moedaOrigem) { "R$" -> "BRL"; "$" -> "USD"; "BTC" -> "BTC"; else -> "" }
        val destino = when (moedaDestino) { "R$" -> "BRL"; "$" -> "USD"; "BTC" -> "BTC"; else -> "" }
        val pair = "${origem}-${destino}"

        // Chama a API de cotação
        RetrofitClient.api.getCotacao(pair).enqueue(object : Callback<Map<String, AwesomeApiService.CotacaoResponse>> {
            override fun onResponse(
                call: Call<Map<String, AwesomeApiService.CotacaoResponse>>,
                response: Response<Map<String, AwesomeApiService.CotacaoResponse>>
            ) {
                binding.progressBar.visibility = View.GONE
                binding.btnConverter.isEnabled = true

                // Pega a cotação da resposta
                val cotacaoObj = response.body()?.values?.firstOrNull()
                val cotacao = cotacaoObj?.bid?.toDoubleOrNull()
                if (cotacao != null) {
                    val convertido = valor * cotacao

                    // Atualiza a carteira
                    carteira.converter(moedaOrigem, moedaDestino, valor, convertido)

                    // Atualiza resultado na tela
                    binding.txtResultado.text = "Valor convertido: $moedaOrigem ${FormatUtil.format(moedaOrigem, valor)} → $moedaDestino ${FormatUtil.format(moedaDestino, convertido)}"

                    // Retorna a carteira atualizada para a MainActivity
                    val intent = intent
                    intent.putExtra("carteira", carteira)
                    setResult(Activity.RESULT_OK, intent)
                } else {
                    showMsg("Falha ao obter cotação.")
                }
            }

            override fun onFailure(
                call: Call<Map<String, AwesomeApiService.CotacaoResponse>>,
                t: Throwable
            ) {
                binding.progressBar.visibility = View.GONE
                binding.btnConverter.isEnabled = true
                showMsg("Erro de conexão ao consultar a cotação.")
            }
        })
    }

    private fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}

