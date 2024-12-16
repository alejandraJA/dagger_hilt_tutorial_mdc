package com.example.gob_fact.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.FragmentMainBinding
import com.example.gob_fact.ui.main.fact.FactFragment.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.example.gob_fact.ui.main.home.adapter.FactAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentMainBinding
    private val facts = mutableListOf<FactEntity>()
    lateinit var adapter: FactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        if (!checkLocationPermissions()) requestLocationPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun checkLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
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
        viewModel.searchFacts(null)
        binding.searchFactsView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchFacts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter.clear()
                    viewModel.searchFacts(it)
                }
                return true
            }
        })
    }

    private fun initRecycler() {
        adapter = FactAdapter(facts, factInterface = { position ->
            val fact = facts[position]
            val bundle = Bundle().apply {
                putString("fact_id", fact.id)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_fragment_fact, bundle
            )
        }, loadMoreFacts = {})
        binding.recyclerFact.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        viewModel.facts.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.recyclerFact.visibility = View.GONE
                binding.noDataLayout.visibility = View.VISIBLE
            } else {
                adapter.addFacts(it)
                adapter.setLoading(false)
                adapter.notifyDataSetChanged()
                binding.recyclerFact.visibility = View.VISIBLE
                binding.noDataLayout.visibility = View.GONE
            }
        }
    }
}