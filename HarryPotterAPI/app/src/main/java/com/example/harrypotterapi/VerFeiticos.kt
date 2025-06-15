package com.example.harrypotterapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypotterapi.api.RetrofitInstance
import com.example.harrypotterapi.databinding.ActivityVerFeiticosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//  Mostra uma lista (RecyclerView) de feitiços
class VerFeiticos : AppCompatActivity() {
    private lateinit var binding: ActivityVerFeiticosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerFeiticosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerFeiticos.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val feiticos = RetrofitInstance.api.getFeiticos()
                val adapter = FeiticoAdapter(feiticos)
                binding.recyclerFeiticos.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
