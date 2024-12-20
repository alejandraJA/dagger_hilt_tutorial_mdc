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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.FragmentMainBinding
import com.example.gob_fact.ui.main.fact.FactFragment.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.example.gob_fact.ui.main.home.adapter.FactAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentMainBinding
    private val facts = mutableListOf<FactEntity>()
    private lateinit var adapter: FactAdapter

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
        binding.searchFactsView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {

                }
                return true
            }
        })
    }

    private fun initRecycler() {
        adapter = FactAdapter(factInterface = { position ->
            val fact = facts[position]
            val bundle = Bundle().apply {
                putString("fact_id", fact.id)
            }
            findNavController().navigate(
                R.id.action_mainFragment_to_fragment_fact, bundle
            )
        })
        binding.recyclerFact.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.facts
                    .catch {
                        adapter.submitData(PagingData.empty())
                        adapter.notifyDataSetChanged()
                        Timber.tag("HomeFragment").e("Error: ${it.message}")
                    }
                    .collect { pagingData ->
                        adapter.submitData(pagingData)
                    }
            }
        }
    }
}