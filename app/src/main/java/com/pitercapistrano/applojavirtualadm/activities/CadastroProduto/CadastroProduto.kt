package com.pitercapistrano.applojavirtualadm.activities.CadastroProduto

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.databinding.ActivityCadastroProdutoBinding

class CadastroProduto : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroProdutoBinding
    private var fotoProduto: Uri? = null

    private var selecionarFoto = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri != null){
            fotoProduto = uri
            binding.imgProduto.setImageURI(fotoProduto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btAddImagem.setOnClickListener {
            selecionarFoto.launch("image/*")
        }
    }
}