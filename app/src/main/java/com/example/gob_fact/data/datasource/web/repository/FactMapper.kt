package com.example.gob_fact.data.datasource.web.repository

import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.models.response.GobFactsResponse

object FactMapper {
    fun mapGobFactsResponseToEntities(response: GobFactsResponse): List<FactEntity> {
        return response.facts.map {
            FactEntity(
                id = it.id,
                columns = it.columns,
                createdAt = it.createdAt,
                dataset = it.dataset,
                dateInsert = it.dateInsert,
                fact = it.fact,
                operations = it.operations,
                organization = it.organization,
                resource = it.resource,
                slug = it.slug,
                url = it.url
            )
        }
    }
}