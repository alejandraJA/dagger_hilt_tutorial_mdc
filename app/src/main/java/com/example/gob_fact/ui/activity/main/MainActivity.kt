package com.example.gob_fact.ui.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.ActivityMainBinding
import com.example.gob_fact.ui.activity.main.adapter.FactAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val facts = mutableListOf<FactEntity>()
    private lateinit var adapter: FactAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setObservers(binding, viewModel)
        setContentView(binding.root)
        initRecycler(binding)
    }

    private fun initRecycler(binding: ActivityMainBinding) {
        adapter = FactAdapter(facts) { position ->
            val fact = facts[position]

        }
        binding.recyclerFact.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers(binding: ActivityMainBinding, viewModel: MainViewModel) {
        viewModel.facts.observe(this) {
            facts.clear()
            facts.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}