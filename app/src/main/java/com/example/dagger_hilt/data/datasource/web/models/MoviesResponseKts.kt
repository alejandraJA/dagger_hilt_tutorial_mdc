package com.example.dagger_hilt.data.datasource.web.models

import com.google.gson.annotations.SerializedName

data class MoviesResponseKts (
    @SerializedName("results")
    val listMovies: List<MovieModelKts>,

    @SerializedName("total_results")
    val totalResults: Int
)