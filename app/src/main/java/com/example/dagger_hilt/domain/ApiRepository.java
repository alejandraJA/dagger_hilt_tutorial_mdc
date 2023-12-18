package com.example.dagger_hilt.domain;

import androidx.annotation.NonNull;

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao;
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;
import com.example.dagger_hilt.data.datasource.web.api.MovieService;
import com.example.dagger_hilt.data.datasource.web.models.MovieModel;
import com.example.dagger_hilt.data.datasource.web.models.MoviesResponse;
import com.example.dagger_hilt.sys.util.Constants;
import com.example.dagger_hilt.sys.util.Resource;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {
    @Inject
    MovieDao dao;

    @Inject
    MovieService movieService;

    @Inject
    public ApiRepository() {
    }

    public void loadMovies(ResultInterface function) {
        if (dao.countMovies() == 0) {
            Call<MoviesResponse> moviesResponseCall = movieService.loadMovies(Constants.API_KEY);
            moviesResponseCall.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> moviesResponse) {
                    function.invoke(Resource.loading());
                    if (moviesResponse.code() == 200) {
                        var movies = moviesResponse.body();
                        if (movies.getTotalResults() == 0) {
                            function.invoke(Resource.blank());
                            return;
                        }
                        for (var movie : movies.getListMovies()) {
                            setMovie(movie);
                        }
                        function.invoke(Resource.success(""));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    function.invoke(Resource.error(t.getMessage()));
                }
            });
        } else function.invoke(Resource.success(""));
    }

    private void setMovie(MovieModel movie) {
        var executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> dao.setMovie(new MovieEntity(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                false)));
    }
}

