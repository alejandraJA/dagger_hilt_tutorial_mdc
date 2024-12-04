package com.example.gob_fact.ui.fragment.main

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.ActivityMainBinding
import com.example.gob_fact.databinding.FragmentMainBinding
import com.example.gob_fact.ui.activity.main.adapter.FactAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    private val facts = mutableListOf<FactEntity>()
    private lateinit var adapter: FactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initRecycler()
        initSearchFact()
    }

    private fun initSearchFact() {
        viewModel.searchFact(null)
        binding.searchFactsView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchFact(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFact(newText)
                return true
            }
        })
    }

    private fun initRecycler() {
        adapter = FactAdapter(facts) { position ->
            val fact = facts[position]
            val bundle = Bundle().apply {
                putString("fact_id", fact.id)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_fragment_fact,
                bundle
            )
        }
        binding.recyclerFact.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.facts.observe(viewLifecycleOwner) {
            facts.clear()
            facts.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}