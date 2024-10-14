package com.pitercapistrano.applojavirtualadm.activities.EditarPedido

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.databinding.ActivityEditarPedidoBinding
import com.pitercapistrano.applojavirtualadm.databinding.ActivityEditarProdutoBinding
import com.pitercapistrano.applojavirtualadm.datasource.DB

class EditarPedido : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPedidoBinding
    private var status_pagamento = "Status de Pagamento: Aguardando Pagamento!"
    private var status_entrega = "Status de Entrega: Em Andamento!"
    private var db = DB()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditarPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usuarioID = intent.extras?.getString("usuarioID").toString()
        val pedidoID = intent.extras?.getString("pedidoID").toString()

        binding.btPgAprovado.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                status_pagamento = "Status de Pagamento: Pagamento Aprovado!"
            }
        }

        binding.btPgEstornado.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                status_pagamento = "Status de Pagamento: Pagamento Estornado!"
            }
        }

        binding.btPgCancelado.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                status_pagamento = "Status de Pagamento: Pagamento Cancelado!"
            }
        }

        binding.btEmSeparacao.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                status_entrega = "Status de Entrega: Em Separação!"
            }
        }

        binding.btEmTransito.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                status_entrega = "Status de Entrega: Em Trânsito!"
            }
        }

        binding.btEntregue.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                status_entrega = "Status de Entrega: Entregue!"
            }
        }

        binding.btAtualizarPedido.setOnClickListener {

            db.atualizarPedido(status_pagamento, status_entrega,usuarioID, pedidoID)
            db.atualizarPedidoAdm(status_pagamento, status_entrega, pedidoID, this)

        }
    }
}