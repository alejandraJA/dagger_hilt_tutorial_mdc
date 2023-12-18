package com.example.dagger_hilt.data.datasource.web.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    List<MovieModel> listMovies;

    @SerializedName("total_results")
    int totalResults;

    public List<MovieModel> getListMovies() {
        return listMovies;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
