package com.example.harrypotterapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterapi.model.Feitico

// Adaptador do RecyclerView - Pega a lista de feitiços e transforma em itens visuais
class FeiticoAdapter(
    private val lista: List<Feitico>
) : RecyclerView.Adapter<FeiticoAdapter.FeiticoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeiticoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feitico, parent, false)
        return FeiticoViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: FeiticoViewHolder, position: Int) {
        val feitico = lista[position]
        holder.nome.text = feitico.name
        holder.descricao.text = feitico.description ?: "Sem descrição"
    }

    class FeiticoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.txtNomeFeitico)
        val descricao: TextView = itemView.findViewById(R.id.txtDescricaoFeitico)
    }
}