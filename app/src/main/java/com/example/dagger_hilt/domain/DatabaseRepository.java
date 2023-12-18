package com.example.dagger_hilt.domain;

import androidx.lifecycle.LiveData;

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao;
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;

import java.util.List;

import javax.inject.Inject;

public class DatabaseRepository {

    @Inject
    MovieDao movieDao;

    @Inject
    public DatabaseRepository() {
    }

    public LiveData<List<MovieEntity>> getMovies() {
        return movieDao.getMovies();
    }

    public void updateMovie(int id, boolean check) {
      movieDao.updateMovie(id, check);
    }

}
