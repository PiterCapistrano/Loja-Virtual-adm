package com.pitercapistrano.applojavirtualadm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pitercapistrano.applojavirtualadm.R
import com.pitercapistrano.applojavirtualadm.adapter.ProdutoAdapter
import com.pitercapistrano.applojavirtualadm.databinding.FragmentProdutosBinding
import com.pitercapistrano.applojavirtualadm.datasource.DB
import com.pitercapistrano.applojavirtualadm.model.Produto

class ProdutosFragment : Fragment() {

    private lateinit var binding: FragmentProdutosBinding
    private lateinit var produtoAdapter: ProdutoAdapter
    private val listaProdutos: MutableList<Produto> = mutableListOf()
    private val db = DB()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProdutosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewProdutos = binding.recyclerViewProdutos
        recyclerViewProdutos.layoutManager = LinearLayoutManager(context)
        produtoAdapter = ProdutoAdapter(requireContext(), listaProdutos)
        recyclerViewProdutos.setHasFixedSize(true)
        recyclerViewProdutos.adapter = produtoAdapter
        db.getProdutos(listaProdutos, produtoAdapter)
    }
}