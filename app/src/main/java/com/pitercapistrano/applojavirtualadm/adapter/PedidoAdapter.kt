package com.pitercapistrano.applojavirtualadm.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pitercapistrano.applojavirtualadm.activities.EditarPedido.EditarPedido
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

        holder.cardView.setOnClickListener{
            val intent = Intent(context, EditarPedido::class.java)
            intent.putExtra("usuarioID", listaPedidos[position].usuarioID)
            intent.putExtra("pedidoID", listaPedidos[position].pedidoID)
            intent.putExtra("status_pagamento", listaPedidos[position].status_pagamento)
            intent.putExtra("status_entrega", listaPedidos[position].status_entrega)
            context.startActivity(intent)

        }

        if (holder.status_pagamento.text.equals("Status de Pagamento: Pagamento Estornado!")){
            holder.status_pagamento.setTextColor(Color.RED)

        } else if (holder.status_pagamento.text.equals("Status de Pagamento: Pagamento Cancelado!")){
            holder.status_pagamento.setTextColor(Color.RED)
        }else{
            holder.status_pagamento.setTextColor(Color.parseColor("#007A05"))
        }

        if (holder.status_entrega.text.equals("Status de Entrega: Em Separação!")){
            holder.status_entrega.setTextColor(Color.parseColor("#FF9800"))
        } else if (holder.status_entrega.text.equals("Status de Entrega: Em Trânsito!")){
            holder.status_entrega.setTextColor(Color.parseColor("#007A05"))
        } else if (holder.status_entrega.text.equals("Status de Entrega: Entregue!")){
            holder.status_entrega.setTextColor(Color.BLUE)
        }

    }

    inner class PedidoViewHolder(binding: PedidoItemBinding): RecyclerView.ViewHolder(binding.root){
        val endereco = binding.txtEndereco
        val telefone = binding.txtTelefone
        val produto = binding.txtProduto
        val preco = binding.txtPreco
        val tamanho_calcado = binding.txtTamanho
        val status_pagamento = binding.txtStatusPagamento
        val status_entrega = binding.txtStatusEntrega
        val cardView = binding.cardView
    }
}