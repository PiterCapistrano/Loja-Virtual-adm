package com.pitercapistrano.applojavirtualadm.datasource

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.pitercapistrano.applojavirtualadm.adapter.ProdutoAdapter
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


}