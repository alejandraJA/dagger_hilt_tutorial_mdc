package com.example.gob_fact.data.datasource.web.api

import androidx.lifecycle.LiveData
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import com.example.gob_fact.data.datasource.web.models.response.GobFactsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FactService {

    @GET("gobmx.facts")
    fun loadFacts(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): LiveData<ApiResponse<GobFactsResponse>>
}