package com.example.dagger_hilt

import com.example.dagger_hilt.data.datasource.database.dao.MovieDaoKts
import com.example.dagger_hilt.data.datasource.web.api.MovieServiceKts
import com.example.dagger_hilt.data.datasource.web.repository.MovieRepositoryKts
import com.example.dagger_hilt.sys.util.AppExecutorsKts
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryKtsTest {
    @Mock
    private lateinit var dao: MovieDaoKts

    @Mock
    private lateinit var service: MovieServiceKts

    @Mock
    private lateinit var appExecutor: AppExecutorsKts
    private lateinit var repository: MovieRepositoryKts

    @Before
    fun setUp() {
        repository = MovieRepositoryKts(dao, service, appExecutor)
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