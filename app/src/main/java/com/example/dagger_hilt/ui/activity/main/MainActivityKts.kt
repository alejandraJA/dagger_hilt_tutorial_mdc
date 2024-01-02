package com.example.dagger_hilt.ui.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import com.example.dagger_hilt.databinding.ActivityMainBinding
import com.example.dagger_hilt.sys.util.ConstantsKts
import com.example.dagger_hilt.ui.activity.main.adapter.MovieAdapterKts
import com.example.dagger_hilt.ui.activity.main.viewModel.MainViewModelKts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityKts : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MainViewModelKts::class.java]
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        val movieList: MutableList<MovieEntityKts>
        movieList = ArrayList()
        val movieAdapterKts =
            MovieAdapterKts(movieList) { id: Int, check: Boolean ->
                viewModel.updateMovie(id, check)
            }
        binding.recyclerMovie.setHasFixedSize(true)
        binding.recyclerMovie.adapter = movieAdapterKts
        /**
         * En caso de que se trate de un Fragment,
         * this será remplazado por viewLifecycleOwner
         */
        viewModel.moviesList.observe(this) { resource ->
            if (resource.status == ConstantsKts.StatusResponse.LOADING) Toast.makeText(
                this,
                "Loading",
                Toast.LENGTH_LONG
            ).show()
            if (resource.status == ConstantsKts.StatusResponse.ERROR) Toast.makeText(
                this,
                "Error",
                Toast.LENGTH_LONG
            ).show()
            if (resource.status == ConstantsKts.StatusResponse.SUCCESS) {
                val it = resource.data
                movieList.clear()
                movieList.addAll(it!!)
                movieAdapterKts.notifyDataSetChanged()
            }
        }
    }
}