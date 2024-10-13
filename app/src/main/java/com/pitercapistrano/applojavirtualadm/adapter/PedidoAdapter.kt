package com.pitercapistrano.applojavirtualadm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pitercapistrano.applojavirtualadm.databinding.PedidoItemBinding
import com.pitercapistrano.applojavirtualadm.model.Pedido

class PedidoAdapter(private val context: Context, private val listaPedidos: MutableList<Pedido>):
    RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val itemLista = PedidoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PedidoViewHolder(itemLista)
    }

    override fun getItemCount() = listaPedidos.size

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        holder.endereco.text = "Endereço:\n${listaPedidos[position].endereco}"
        holder.telefone.text = listaPedidos[position].telefone
        holder.produto.text = listaPedidos[position].produto
        holder.preco.text = listaPedidos[position].preco
        holder.tamanho_calcado.text = listaPedidos[position].tamanho_calcado
        holder.status_pagamento.text = listaPedidos[position].status_pagamento
        holder.status_entrega.text = listaPedidos[position].status_entrega

    }

    inner class PedidoViewHolder(binding: PedidoItemBinding): RecyclerView.ViewHolder(binding.root){
        val endereco = binding.txtEndereco
        val telefone = binding.txtTelefone
        val produto = binding.txtProduto
        val preco = binding.txtPreco
        val tamanho_calcado = binding.txtTamanho
        val status_pagamento = binding.txtStatusPagamento
        val status_entrega = binding.txtStatusEntrega
    }
}