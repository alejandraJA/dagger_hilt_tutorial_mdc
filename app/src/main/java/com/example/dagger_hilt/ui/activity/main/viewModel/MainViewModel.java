package com.example.dagger_hilt.ui.activity.main.viewModel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.domain.DatabaseRepository;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @Inject
    DatabaseRepository databaseRepository;

    @Inject
    public MainViewModel() {
    }

    public void updateMovie(int id, boolean check) {
        var executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> databaseRepository.updateMovie(id, check));
    }

    public MediatorLiveData<List<MovieEntity>> getMovies() {
        var movies = new MediatorLiveData<List<MovieEntity>>();
        movies.addSource(databaseRepository.getMovies(), movies::postValue);
        return movies;
    }
}
