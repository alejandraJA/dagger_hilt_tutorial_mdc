package com.example.dagger_hilt.data.datasource.web.models

import com.google.gson.annotations.SerializedName
import com.example.dagger_hilt.data.datasource.web.models.MovieModel

data class MoviesResponse (
    @SerializedName("results")
    val listMovies: List<MovieModel>,

    @SerializedName("total_results")
    val totalResults: Int
)