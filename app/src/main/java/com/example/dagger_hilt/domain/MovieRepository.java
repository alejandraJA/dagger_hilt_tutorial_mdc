package com.example.dagger_hilt.domain;

import androidx.lifecycle.LiveData;

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao;
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.data.datasource.web.api.MovieService;
import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponse;
import com.example.dagger_hilt.data.datasource.web.models.MovieModel;
import com.example.dagger_hilt.data.datasource.web.models.MoviesResponse;
import com.example.dagger_hilt.sys.util.AppExecutors;
import com.example.dagger_hilt.sys.util.Constants;
import com.example.dagger_hilt.sys.util.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MovieRepository {
    private final MovieDao dao;
    private final MovieService service;
    private final AppExecutors appExecutors;

    @Inject
    public MovieRepository(MovieDao dao, MovieService service, AppExecutors appExecutors) {
        this.dao = dao;
        this.service = service;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<MovieEntity>>> loadMovies() {
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(appExecutors) {
            @Override
            protected void saveCallResult(MoviesResponse moviesResponse) {
                List<MovieEntity> moviesList = new ArrayList<>();
                for (MovieModel movie : moviesResponse.getListMovies()) {
                    moviesList.add(new MovieEntity(
                            movie.getId(),
                            movie.getTitle(),
                            movie.getOriginalTitle(),
                            movie.getOverview(),
                            movie.getPosterPath(),
                            false
                    ));
                }
                dao.setMovies(moviesList);
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return dao.getMovies();
            }

            @Override
            protected LiveData<ApiResponse> createCall() {
                return ApiResponse.create(service.loadMovies(Constants.API_KEY));
            }
        }.asLiveData();
    }

    public void updateMovie(int id, Boolean check) {
        dao.updateMovie(id, check);
    }
}
