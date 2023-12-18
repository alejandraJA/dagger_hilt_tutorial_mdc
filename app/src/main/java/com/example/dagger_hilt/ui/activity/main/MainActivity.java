package com.example.dagger_hilt.ui.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.databinding.ActivityMainBinding;
import com.example.dagger_hilt.ui.activity.main.adapter.MovieAdapter;
import com.example.dagger_hilt.ui.activity.main.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        var binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<MovieEntity> movieList = new ArrayList<>();
        var movieAdapter = new MovieAdapter(movieList, viewModel::updateMovie);
        binding.recyclerMovie.setHasFixedSize(true);
        binding.recyclerMovie.setAdapter(movieAdapter);
        /*
          En caso de que se trate de un Fragment,
          this será remplazado por viewLifecycleOwner
         */
        viewModel.getMovies().observe(this, movies -> {
            movieList.clear();
            movieList.addAll(movies);
            movieAdapter.notifyDataSetChanged();
        });
    }
}
