package com.example.finapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.R
import com.example.finapp.Transacao

class TransacaoAdapter(private val lista: List<Transacao>) :
    RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder>() {

    // ViewHolder: mantém as views da célula
    class TransacaoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTipo: ImageView = view.findViewById(R.id.imgTipo)
        val txtDescricao: TextView = view.findViewById(R.id.txtDescricao)
        val txtValor: TextView = view.findViewById(R.id.txtValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transacao, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = lista[position]

        holder.txtDescricao.text = transacao.descricao

        val valorFormatado = if (transacao.tipo == "DEBITO") {
            "- R$ %.2f".format(transacao.valor)
        } else {
            "+ R$ %.2f".format(transacao.valor)
        }
        holder.txtValor.text = valorFormatado

        val cor = if (transacao.tipo == "DEBITO") {
            Color.RED
        } else {
            Color.rgb(0, 128, 0)
        }
        holder.txtValor.setTextColor(cor)

        val imagem = if (transacao.tipo == "DEBITO") {
            R.drawable.debito
        } else {
            R.drawable.credito
        }
        holder.imgTipo.setImageResource(imagem)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}
