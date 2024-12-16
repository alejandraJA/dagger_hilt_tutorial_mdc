package com.example.gob_fact.ui.fragment.home

import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import com.example.gob_fact.fake.FakeFactDao
import com.example.gob_fact.fake.FakeFactService
import com.example.gob_fact.sys.util.AppExecutors
import com.example.gob_fact.ui.main.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var factRepository: FactRepository
    private lateinit var fakeFactDao: FakeFactDao
    private lateinit var fakeFactService: FakeFactService
    private lateinit var expectedFact: AppExecutors

    @Before
    fun setUp() {
        fakeFactDao = FakeFactDao()
        fakeFactService = FakeFactService()
        expectedFact = AppExecutors()
        factRepository = FactRepository(fakeFactDao, fakeFactService, expectedFact)
        homeViewModel = HomeViewModel(factRepository)
    }

    @Test
    fun getFacts() {
        fakeFactDao.setFact(
            FactEntity(
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

        homeViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                homeViewModel.facts.observeForever {
                    assert(it.isNotEmpty())
                }
            }
        }
    }

    @Test
    fun loadMoreFacts() {
        fakeFactDao.setFact(
            FactEntity(
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
        fakeFactDao.setFact(
            FactEntity(
                id = "1",
                columns = "columns",
                createdAt = 1,
                dataset = "dataset",
                dateInsert = "dateInsert",
                fact = "fact",
                operations = "operations",
                organization = "query",
                resource = "resource",
                slug = "slug",
                url = "url"
            )
        )

        homeViewModel.loadMoreFacts("query")
        homeViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                homeViewModel.facts.observeForever {
                    assert(it.isNotEmpty())
                }
            }
        }
    }

    @Test
    fun searchFacts() {
        fakeFactDao.setFact(
            FactEntity(
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
        fakeFactDao.setFact(
            FactEntity(
                id = "1",
                columns = "columns",
                createdAt = 1,
                dataset = "dataset",
                dateInsert = "dateInsert",
                fact = "fact",
                operations = "operations",
                organization = "query",
                resource = "resource",
                slug = "slug",
                url = "url"
            )
        )

        homeViewModel.searchFacts("query")

        homeViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                homeViewModel.facts.observeForever {
                    assert(it.isNotEmpty())
                }
            }
        }
    }
}