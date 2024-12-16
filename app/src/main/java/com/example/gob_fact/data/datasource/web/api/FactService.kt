package com.example.gob_fact.data.datasource.web.api

import androidx.lifecycle.LiveData
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import com.example.gob_fact.data.datasource.web.models.response.GobFactsResponse
import retrofit2.http.GET

interface FactService {

    @GET("gobmx.facts")
    fun loadFacts(): LiveData<ApiResponse<GobFactsResponse>>
}