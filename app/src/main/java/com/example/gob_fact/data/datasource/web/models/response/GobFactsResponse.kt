package com.example.gob_fact.data.datasource.web.models.response

import com.example.gob_fact.data.datasource.web.models.Facts
import com.example.gob_fact.data.datasource.web.models.Pagination
import com.google.gson.annotations.SerializedName

data class GobFactsResponse(
    val pagination: Pagination,
    @SerializedName("results")
    val facts: List<Facts>
)