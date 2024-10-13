package com.pitercapistrano.applojavirtualadm.activities.CadastroProduto

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.databinding.ActivityCadastroProdutoBinding
import com.pitercapistrano.applojavirtualadm.datasource.DB

class CadastroProduto : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroProdutoBinding
    private var fotoProduto: Uri? = null
    private var db = DB()

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

        binding.btAdicionarProduto.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val preco = binding.editPreco.text.toString()
            val idProduto = binding.editIdProduto.text.toString()

            // Esconde o teclado ao tocar no botão
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

            if (nome.isEmpty() || preco.isEmpty() || idProduto.isEmpty()){
                val snackbar = Snackbar.make(this, binding.main, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                db.cadastroProdutos(fotoProduto!!, nome, preco, idProduto)
                Toast.makeText(this,"Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                binding.editNome.text.clear()
                binding.editPreco.text.clear()
                binding.editIdProduto.text.clear()
            }
        }
    }
}