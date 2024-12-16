package com.example.gob_fact.ui.act.start

import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import com.example.gob_fact.fake.FakeFactDao
import com.example.gob_fact.fake.FakeFactService
import com.example.gob_fact.sys.util.AppExecutors
import com.example.gob_fact.ui.start.StartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.junit.Before
import org.junit.Test

class StartViewModelTest {
    private lateinit var viewModel: StartViewModel
    private lateinit var repository: FactRepository
    private lateinit var fakeFactDao: FakeFactDao
    private lateinit var fakeFactService: FakeFactService
    private lateinit var executors: AppExecutors

    @Before
    fun setUp() {
        fakeFactDao = FakeFactDao()
        fakeFactService = FakeFactService()
        executors = AppExecutors()
        repository = FactRepository(fakeFactDao, fakeFactService, executors)
        viewModel = StartViewModel(repository)
    }

    @Test
    fun getFacts() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.facts.addSource(viewModel.facts) { facts ->
                    assert(facts.isNotEmpty())
                }
            }
        }
    }
}