package com.example.gob_fact.data.datasource.web.models

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("results")
    val listMovies: List<MovieModel>,

    @SerializedName("total_results")
    val totalResults: Int
)