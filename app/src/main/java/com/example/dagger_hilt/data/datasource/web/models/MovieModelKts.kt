package com.example.dagger_hilt.data.datasource.web.models

import com.google.gson.annotations.SerializedName

data class MovieModelKts (
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("original_title")
    var originalTitle: String,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("poster_path")
    var posterPath: String,
)