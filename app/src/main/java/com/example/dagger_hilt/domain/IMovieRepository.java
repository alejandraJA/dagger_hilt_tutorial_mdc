package com.example.dagger_hilt.domain;

import androidx.lifecycle.LiveData;

import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.sys.util.Resource;

import java.util.List;

public interface IMovieRepository {
    LiveData<Resource<List<MovieEntity>>> loadMovies();

    void updateMovie(int id, Boolean check);
}
