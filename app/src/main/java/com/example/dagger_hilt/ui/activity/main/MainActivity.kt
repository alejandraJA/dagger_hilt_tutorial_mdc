package com.example.dagger_hilt.ui.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity
import com.example.dagger_hilt.databinding.ActivityMainBinding
import com.example.dagger_hilt.ui.activity.main.adapter.MovieAdapter
import com.example.dagger_hilt.ui.activity.main.viewModel.MainViewModel
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
        val movieList: MutableList<MovieEntity>
        movieList = ArrayList()
        val movieAdapter =
            MovieAdapter(movieList) { id: Int, check: Boolean ->
                viewModel.updateMovie(id, check)
            }
        binding.recyclerMovie.setHasFixedSize(true)
        binding.recyclerMovie.adapter = movieAdapter
        viewModel.movies.observe(this) {
            movieList.clear()
            movieList.addAll(it!!)
            movieAdapter.notifyDataSetChanged()
        }
    }
}