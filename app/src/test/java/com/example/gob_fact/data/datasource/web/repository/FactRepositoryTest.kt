package com.example.gob_fact.data.datasource.web.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.api.FactService
import com.example.gob_fact.data.datasource.web.models.response.ApiSuccessResponse
import com.example.gob_fact.fake.FakeFactDao
import com.example.gob_fact.fake.FakeFactService
import com.example.gob_fact.sys.util.AppExecutors
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class FactRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testFact = FactEntity(
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

    private val fakeFactDao = FakeFactDao()

    private val fakeFactService: FactService = FakeFactService()

    @Mock
    private lateinit var mockAppExecutors: AppExecutors

    private lateinit var factRepository: FactRepository

    @Before
    fun setUp() {
        factRepository = FactRepository(
            dao = fakeFactDao,
            service = fakeFactService,
            appExecutor = mockAppExecutors
        )
    }

    @Test
    fun `loadFacts with empty database should fetch from network`() {
        // Act
        factRepository.loadFacts(params.loadSize, (nextPage - 1) * params.loadSize)

        // Assert
        assert(fakeFactService.loadFacts().value is ApiSuccessResponse)
    }

    @Test
    fun `deleteFacts should remove all facts`() {
        // Arrange
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        // Act
        factRepository.deleteFacts()

        // Assert
        assert(fakeFactDao.getFacts().value!!.isEmpty())
    }

    @Test
    fun `getFacts should return all facts`() {
        // Arrange
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)

        // Act
        val result = factRepository.getFacts()

        // Assert
        assert(result.value!!.isNotEmpty())
    }

    @Test
    fun `getFacts should return empty list if no facts exist`() {
        // Arrange
        // Act
        val result = factRepository.getFacts()

        // Assert
        assert(result.value!!.isEmpty())
    }

    @Test
    fun `getFactsPaginated should return correct number of facts`() {
        // Arrange
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)

        // Act
        val result = factRepository.getFactsPaginated(10, 0)

        // Assert
        assert(result.size == 10)
    }

    @Test
    fun `searchFactPaginated should return correct number of facts`() {
        // Arrange
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)

        // Act
        val result = factRepository.searchFactPaginated("organization", 10, 0)

        // Assert
        assert(result.size == 10)
    }

    @Test
    fun `getFact should return correct fact`() {
        // Arrange
        fakeFactDao.setFact(testFact)

        // Act
        val result = factRepository.getFact("1")

        // Assert
        assert(result.value == testFact)
    }

    @Test
    fun `getFact should return null if fact does not exist`() {
        // Arrange
        fakeFactDao.setFact(testFact)
        // Act
        val result = factRepository.getFact("12")

        // Assert
        assert(result.value == null)
    }

    @Test
    fun `searchFact should return correct facts`() {
        // Arrange
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)
        fakeFactDao.setFact(testFact)

        // Act
        val result = factRepository.searchFact("organization")

        // Assert
        assert(result.value!!.size == 3)
    }
}

