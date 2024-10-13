package com.pitercapistrano.applojavirtualadm.datasource

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

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
}