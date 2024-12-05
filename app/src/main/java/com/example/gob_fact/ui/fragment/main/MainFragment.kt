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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.FragmentMainBinding
import com.example.gob_fact.ui.fragment.main.adapter.FactAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentMainBinding
    private val facts = mutableListOf<FactEntity>()
    lateinit var adapter: FactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setObservers()
    }

    private fun setupUI() {
        initRecycler()
        initSearchFact()
    }

    private fun initSearchFact() {
        viewModel.loadMoreFacts(null)
        binding.searchFactsView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    adapter.clear()
                    viewModel.searchFacts(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun initRecycler() {
        adapter = FactAdapter(facts,
            factInterface = { position ->
                val fact = facts[position]
                val bundle = Bundle().apply {
                    putString("fact_id", fact.id)
                }
                findNavController().navigate(
                    R.id.action_mainFragment_to_fragment_fact,
                    bundle
                )
            },
            loadMoreFacts = {
                viewModel.loadMoreFacts(null)
            })
        binding.recyclerFact.adapter = adapter
        binding.recyclerFact.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition + 5 >= totalItemCount) {
                    viewModel.loadMoreFacts(null)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.facts.observe(viewLifecycleOwner) {
            adapter.addFacts(it)
            adapter.setLoading(false)
            adapter.notifyDataSetChanged()
        }
    }
}