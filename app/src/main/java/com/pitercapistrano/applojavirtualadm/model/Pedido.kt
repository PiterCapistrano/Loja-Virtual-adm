package com.pitercapistrano.applojavirtualadm.model

data class Pedido(
    val endereco: String? = null,
    val telefone: String? = null,
    val produto: String? = null,
    val preco: String? = null,
    val tamanho_calcado: String? = null,
    val status_pagamento: String? = null,
    val status_entrega: String? = null,
    val usuarioID: String? = null,
    val pedidoID: String? = null
)
