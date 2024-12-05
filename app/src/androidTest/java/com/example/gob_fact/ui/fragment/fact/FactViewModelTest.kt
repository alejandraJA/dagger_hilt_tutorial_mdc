package com.example.gob_fact.ui.fragment.fact

import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import com.example.gob_fact.fake.FakeFactDao
import com.example.gob_fact.fake.FakeFactService
import com.example.gob_fact.sys.util.AppExecutors
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FactViewModelTest {

    private lateinit var factViewModel: FactViewModel
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
        factViewModel = FactViewModel(factRepository)
    }

    @Test
    fun getFactId() {
        // Act
        val expected = "1"
        factViewModel.factId = expected
        val result = factViewModel.factId

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun setFactId() {
        // Act
        val expected = "1"
        factViewModel.factId = expected
        val result = factViewModel.factId

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun getFact() {
        // Arrange
        fakeFactDao.setFact(FactEntity(
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
        ))
        factViewModel.factId = "1"
        factViewModel.loadFact()

        // Act
        val result = factViewModel.fact

        // Assert
        assertNotNull(result)
    }

    @Test
    fun loadFact() {
        // Arrange
        fakeFactDao.setFact(FactEntity(
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
        ))
        factViewModel.factId = "1"

        // Act
        factViewModel.loadFact()

        // Assert
        assertNotNull(factViewModel.fact)
    }
}