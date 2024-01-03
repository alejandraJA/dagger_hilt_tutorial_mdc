package com.example.dagger_hilt.ui.activity.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.domain.MovieRepository;
import com.example.dagger_hilt.sys.util.Resource;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final MovieRepository repository;
    private final LiveData<Resource<List<MovieEntity>>> moviesList;

    @Inject
    public MainViewModel(MovieRepository repository) {
        this.repository = repository;
        moviesList = repository.loadMovies();
    }

    public void updateMovie(int id, boolean check) {
        var executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> repository.updateMovie(id, check));
    }

    public LiveData<Resource<List<MovieEntity>>> getMoviesList() {
        return moviesList;
    }
}
