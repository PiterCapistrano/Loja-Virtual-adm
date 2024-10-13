package com.pitercapistrano.applojavirtualadm.activities.EditarProduto

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.activities.Home.Home
import com.pitercapistrano.applojavirtualadm.databinding.ActivityEditarProdutoBinding
import com.pitercapistrano.applojavirtualadm.databinding.ActivityHomeBinding
import com.pitercapistrano.applojavirtualadm.datasource.DB

class EditarProduto : AppCompatActivity() {

    private lateinit var binding: ActivityEditarProdutoBinding
    private var fotoProduto: Uri? = null
    private var db = DB()

    private var selecionarFoto = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        if (uri != null){
            fotoProduto = uri
            binding.imgProduto.setImageURI(fotoProduto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditarProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val codigoProduto = intent.extras!!.getString("idProduto")
        val foto = intent.extras!!.getString("foto")
        val nome = intent.extras!!.getString("nome")
        val preco = intent.extras!!.getString("preco")

        binding.btAddImagem.setOnClickListener {
            selecionarFoto.launch("image/*")
        }

        binding.editNome.setText(nome)
        binding.editPreco.setText(preco)
        binding.editIdProduto.setText(codigoProduto)
        Glide.with(this).load(foto).into(binding.imgProduto)

        binding.btAtualizarProduto.setOnClickListener {
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
            }else if (nome.isNotEmpty() && preco.isNotEmpty() && idProduto.isNotEmpty() && fotoProduto != null){
                db.atualizarPodutoComFoto(
                    fotoProduto!!,
                    nome,
                    preco,
                    idProduto
                )
                Toast.makeText(this,"Atualização efetuada com sucesso!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.VISIBLE
                Handler().postDelayed({
                    finish()
                }, 2000)

            } else {
                db.atulaizarProdutoSemFoto(
                    nome,
                    preco,
                    idProduto
                )
                Toast.makeText(this,"Atualização efetuada com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}