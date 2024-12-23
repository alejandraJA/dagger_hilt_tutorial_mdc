package com.example.gob_fact.data.datasource.web.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class FactPagingSource(
    private val service: FactRepository,
    private val dao: FactDao,
    private val organization: String,
) : PagingSource<Int, FactEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FactEntity> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val nextPage = params.key ?: 1
                val isNextKey: Boolean
                var page = (nextPage - 1) * 10
                if (page == 0) page = 1
                service.loadFacts(10, page)

                val facts: MutableList<FactEntity> = mutableListOf()
                dao.getAllFacts(organization)?.let { facts.addAll(it) }
                if (facts.isEmpty())
                    dao.getAllFacts()?.let { facts.addAll(it) }
                isNextKey = facts.isEmpty()

                LoadResult.Page(
                    data = facts,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (isNextKey) null else nextPage + 1
                )
            } catch (e: Exception) {
                Timber.tag("FactPagingSource").e("Error: ${e.message}")
                LoadResult.Error(e)
            }
        }

    override fun getRefreshKey(state: PagingState<Int, FactEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}