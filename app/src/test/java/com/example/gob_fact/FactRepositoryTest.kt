package com.example.gob_fact

import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.web.api.FactService
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import com.example.gob_fact.sys.util.AppExecutors
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FactRepositoryTest {
    @Mock
    private lateinit var dao: FactDao

    @Mock
    private lateinit var service: FactService

    @Mock
    private lateinit var appExecutor: AppExecutors
    private lateinit var repository: FactRepository

    @Before
    fun setUp() {
        repository = FactRepository(dao, service, appExecutor)
    }

    @Test
    fun updateMovie_shouldUpdateMovieInDb() {
        // Arrange
        val movieId = 1
        val check = true

        // Act
        repository.updateMovie(movieId, check)

        // Assert
        verify(dao).updateMovie(movieId, check)
    }

}