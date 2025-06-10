package com.example.carteiravirtual

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carteiravirtual.databinding.ActivityMainBinding
import com.example.carteiravirtual.model.Carteira
import com.example.carteiravirtual.api.FormatUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var carteira = Carteira()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.btnConverter.setOnClickListener {
            val intent = Intent(this, ConverterActivity::class.java)
            intent.putExtra("carteira", carteira)
            startActivityForResult(intent, 10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            data?.getParcelableExtra<Carteira>("carteira")?.let {
                carteira = it
                updateUI()
            }
        }
    }

    private fun updateUI() {
        binding.txtReal.text = "R$: ${FormatUtil.formatMoney(carteira.real)}"
        binding.txtDolar.text = "$: ${FormatUtil.formatMoney(carteira.dolar)}"
        binding.txtBtc.text = "BTC: ${FormatUtil.formatBTC(carteira.btc)}"
    }
}

