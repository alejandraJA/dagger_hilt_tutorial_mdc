package com.example.gob_fact.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gob_fact.data.datasource.web.api.FactService
import com.example.gob_fact.data.datasource.web.models.Facts
import com.example.gob_fact.data.datasource.web.models.Pagination
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import com.example.gob_fact.data.datasource.web.models.response.GobFactsResponse
import com.example.gob_fact.sys.util.Resource
import retrofit2.Response

class FakeFactService : FactService {
    override fun loadFacts(): LiveData<ApiResponse<GobFactsResponse>> {
        Resource.success(
            GobFactsResponse(
                pagination = Pagination(1, 1, 1),
                facts = listOf(
                    Facts(
                        id = "1",
                        columns = "columns",
                        createdAt = 1,
                        dataset = "dataset",
                        dateInsert = "dateInsert",
                        fact = "fact",
                        operations = "operations",
                        organization = "organization",
                        resource = "resource",
                        slug = "slug",
                        url = "url"
                    )
                ),
            )
        )

        return MutableLiveData(
            ApiResponse.create(
                Response.success(
                    GobFactsResponse(
                        pagination = Pagination(1, 1, 1),
                        facts = listOf(
                            Facts(
                                id = "1",
                                columns = "columns",
                                createdAt = 1,
                                dataset = "dataset",
                                dateInsert = "dateInsert",
                                fact = "fact",
                                operations = "operations",
                                organization = "organization",
                                resource = "resource",
                                slug = "slug",
                                url = "url"
                            )
                        )
                    )
                )
            )
        )
    }
}