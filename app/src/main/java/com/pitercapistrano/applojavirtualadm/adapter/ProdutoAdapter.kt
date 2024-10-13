package com.pitercapistrano.applojavirtualadm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pitercapistrano.applojavirtualadm.activities.EditarProduto.EditarProduto
import com.pitercapistrano.applojavirtualadm.databinding.ProdutosItemBinding
import com.pitercapistrano.applojavirtualadm.datasource.DB
import com.pitercapistrano.applojavirtualadm.model.Produto

class ProdutoAdapter(private val context: Context, private val lista_produtos: MutableList<Produto>):
    RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val itemLista = ProdutosItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProdutoViewHolder(itemLista)
    }

    override fun getItemCount() = lista_produtos.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.codigoProduto.text = "Código: ${lista_produtos[position].idProduto}"
        holder.nomeProduto.text = lista_produtos[position].nome
        holder.precoProduto.text = "R$: ${lista_produtos[position].preco}"
        Glide.with(context).load(lista_produtos[position].foto).into(holder.fotoProduto)

        holder.btAtualizar.setOnClickListener {
            val intent = Intent(context, EditarProduto::class.java)
            intent.putExtra("idProduto", lista_produtos[position].idProduto)
            intent.putExtra("foto", lista_produtos[position].foto)
            intent.putExtra("nome", lista_produtos[position].nome)
            intent.putExtra("preco", lista_produtos[position].preco)
            context.startActivity(intent)
        }

        holder.btDeletar.setOnClickListener {
            val idProduto = lista_produtos[position].idProduto
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Excluir Produto")
            alertDialog.setMessage("Deseja excluir esse produto?")
            alertDialog.setPositiveButton("Sim",{_, _ ->
                val db = DB()
                db.deletarProduto(idProduto!!)
                lista_produtos.removeAt(position)
                notifyDataSetChanged()
            })
            alertDialog.setNegativeButton("Não",{_, _ ->

            })
            alertDialog.show()
        }
    }

    inner class ProdutoViewHolder(binding: ProdutosItemBinding): RecyclerView.ViewHolder(binding.root){
        val codigoProduto = binding.txtIdProduto
        val fotoProduto = binding.fotoProduto
        val nomeProduto = binding.txtNomeProduto
        val precoProduto = binding.txtPrecoProduto
        val btAtualizar = binding.btEditar
        val btDeletar = binding.btDeletar
    }
}