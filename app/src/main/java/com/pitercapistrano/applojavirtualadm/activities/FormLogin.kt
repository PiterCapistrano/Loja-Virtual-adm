package com.pitercapistrano.applojavirtualadm.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.databinding.ActivityFormLoginBinding

class FormLogin : AppCompatActivity() {

private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btEntrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            // Esconde o teclado ao tocar no botão
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

            if (email == "pitercapistrano@gmail.com" && senha == "123456"){
               val snackbar = Snackbar.make(this, binding.main, "Login efetuado com sucesso!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.parseColor("#008105"))
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                binding.progressBar.visibility = View.VISIBLE
                Handler().postDelayed({
                goToHome()
                }, 3000)
            } else if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(this, binding.main, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            } else {
                val snackbar = Snackbar.make(this, binding.main, "Email ou Senha inválidos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.parseColor("#008105"))
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }

        }

    }

    private fun goToHome(){
        val intet = Intent(this, Home::class.java)
        startActivity(intet)
        finish()
    }
}