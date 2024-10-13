package com.pitercapistrano.applojavirtualadm.activities.Home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.ktx.Firebase
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.activities.CadastroProduto.CadastroProduto
import com.pitercapistrano.applojavirtualadm.databinding.ActivityHomeBinding
import com.pitercapistrano.applojavirtualadm.fragments.PedidosFragment
import com.pitercapistrano.applojavirtualadm.fragments.ProdutosFragment

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        window.statusBarColor = Color.parseColor("#000000")
        window.decorView.systemUiVisibility = View.GONE

        fragmentRender(R.id.containerFragmentProdutos, ProdutosFragment())

        binding.btAdd.setOnClickListener{
            val intent = Intent(this, CadastroProduto::class.java)
            startActivity(intent)
        }

        binding.btProdutos.setOnClickListener {
            clicked = true
            if (clicked){
                binding.btProdutos.setBackgroundResource(R.drawable.bt_menu_enabled)
                binding.btProdutos.setTextColor(Color.WHITE)
                binding.btPedidos.setBackgroundResource(R.drawable.bt_menu_disabled)
                binding.btPedidos.setTextColor(Color.BLACK)

                binding.containerFragmentPedidos.visibility = View.GONE
                binding.containerFragmentProdutos.visibility = View.VISIBLE
                fragmentRender(R.id.containerFragmentProdutos, ProdutosFragment())
            }
        }

        binding.btPedidos.setOnClickListener {
            clicked = true
            if (clicked){
                binding.btProdutos.setBackgroundResource(R.drawable.bt_menu_disabled)
                binding.btProdutos.setTextColor(Color.BLACK)
                binding.btPedidos.setBackgroundResource(R.drawable.bt_menu_enabled)
                binding.btPedidos.setTextColor(Color.WHITE)


                binding.containerFragmentProdutos.visibility = View.GONE
                binding.containerFragmentPedidos.visibility = View.VISIBLE
                fragmentRender(R.id.containerFragmentPedidos, PedidosFragment())
            }
        }

    }

    private fun fragmentRender(containerId: Int, fragment: Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerId, fragment)
        fragmentTransaction.commit()
    }
}