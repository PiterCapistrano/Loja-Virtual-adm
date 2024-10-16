package com.pitercapistrano.applojavirtualadm.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.pitercapistrano.applojavirtualadm.activities.Home.Home
import com.pitercapistrano.applojavirtualadm.adapter.PedidoAdapter
import com.pitercapistrano.applojavirtualadm.adapter.ProdutoAdapter
import com.pitercapistrano.applojavirtualadm.model.Pedido
import com.pitercapistrano.applojavirtualadm.model.Produto

class DB {

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun cadastroProdutos(
        foto: Uri,
        nome: String,
        preco: String,
        idProduto: String
                         ){

        val storageReference = storage.getReference("/Produtos/${idProduto}")
        storageReference.putFile(foto).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                var produtoMap = hashMapOf(
                    "foto" to uri.toString(),
                    "nome" to nome,
                    "preco" to preco,
                    "idProduto" to idProduto
                )

                db.collection("Produtos").document(idProduto).set(produtoMap).addOnCompleteListener {

                }.addOnFailureListener {

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getProdutos(listaProdutos: MutableList<Produto>, produtoAdapter: ProdutoAdapter){
        db.collection("Produtos").orderBy("nome").get().addOnCompleteListener { documento ->
            if (documento.isSuccessful){
                for (doc in documento.result){
                    val produto = doc.toObject(Produto::class.java)
                    listaProdutos.add(produto)
                    produtoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun atualizarPodutoComFoto(
        foto: Uri,
        nome: String,
        preco: String,
        idProduto: String){

        val storageReference = storage.getReference("/Produtos/${idProduto}")
        storageReference.putFile(foto).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->

                db.collection("Produtos").document(idProduto).update(
                    "idProduto", idProduto,
                    "foto", uri.toString(),
                    "nome", nome,
                    "preco", preco
                ).addOnCompleteListener {

                }.addOnFailureListener {

                }
            }
        }
    }

    fun atulaizarProdutoSemFoto(
        nome: String,
        preco: String,
        idProduto: String){

        db.collection("Produtos").document(idProduto).update(
            "idProduto", idProduto,
            "nome", nome,
            "preco", preco
        ).addOnCompleteListener {

        }.addOnFailureListener {

        }
    }

    fun deletarProduto(idProduto: String){
        db.collection("Produtos").document(idProduto).delete().addOnCompleteListener {

        }.addOnFailureListener {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPedidos(listaPedidos: MutableList<Pedido>, pedidoAdapter: PedidoAdapter){
        db.collection("Pedidos_Adm").orderBy("status_entrega").get().addOnCompleteListener { documento ->
            if (documento.isSuccessful){
                for (doc in documento.result){
                    val pedido = doc.toObject(Pedido::class.java)
                    listaPedidos.add(pedido)
                    pedidoAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun atualizarPedido(status_pagamento: String, status_entrega: String, usarioID: String, pedidoID: String){
        db.collection("Usuario_Pedidos").document(usarioID).collection("Pedidos")
            .document(pedidoID).update(
                "status_pagamento", status_pagamento,
                "status_entrega", status_entrega
            ).addOnCompleteListener {

            }.addOnFailureListener {

            }
    }

    fun atualizarPedidoAdm(status_pagamento: String, status_entrega: String, pedidoID: String, context: Context){
        db.collection("Pedidos_Adm").document(pedidoID).update(
            "status_pagamento", status_pagamento,
            "status_entrega", status_entrega
        ).addOnCompleteListener {
            val intent = Intent(context, Home::class.java)
            context.startActivity(intent)
        }.addOnFailureListener {

        }
    }
}