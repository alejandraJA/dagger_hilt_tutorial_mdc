package com.example.gob_fact.data.datasource.web.models

import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("_id")
    val id: String,
    val columns: String,
    @SerializedName("created_at")
    val createdAt: Int,
    val dataset: String,
    @SerializedName("date_insert")
    val dateInsert: String,
    val fact: String,
    val operations: String,
    val organization: String,
    val resource: String,
    val slug: String,
    val url: String
)