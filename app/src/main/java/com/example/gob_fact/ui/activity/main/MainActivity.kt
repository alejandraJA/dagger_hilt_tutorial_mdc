package com.example.gob_fact.ui.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.data.datasource.database.entities.MovieEntity
import com.example.gob_fact.databinding.ActivityMainBinding
import com.example.gob_fact.sys.util.Constants
import com.example.gob_fact.ui.activity.main.adapter.FactAdapter
import com.example.gob_fact.ui.activity.main.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        val movieList: MutableList<MovieEntity> = ArrayList()
        val factAdapter =
            FactAdapter(movieList) { id: Int, check: Boolean ->
                viewModel.updateMovie(id, check)
            }
        binding.recyclerMovie.setHasFixedSize(true)
        binding.recyclerMovie.adapter = factAdapter
        /**
         * En caso de que se trate de un Fragment,
         * this será remplazado por viewLifecycleOwner
         */
        viewModel.moviesList.observe(this) { resource ->
            if (resource.status == Constants.StatusResponse.LOADING) Toast.makeText(
                this,
                "Loading",
                Toast.LENGTH_LONG
            ).show()
            if (resource.status == Constants.StatusResponse.ERROR) Toast.makeText(
                this,
                "Error",
                Toast.LENGTH_LONG
            ).show()
            if (resource.status == Constants.StatusResponse.SUCCESS) {
                Toast.makeText(
                    this,
                    "Success",
                    Toast.LENGTH_LONG
                ).show()
                val it = resource.data
                movieList.clear()
                movieList.addAll(it!!)
                factAdapter.notifyDataSetChanged()
            }
        }
    }
}