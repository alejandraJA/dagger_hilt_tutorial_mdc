package com.example.dagger_hilt.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDaoKts {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMovie(movieEntityKts: MovieEntityKts)

    @Query("SELECT * FROM movie")
    fun getMovies(): LiveData<List<MovieEntityKts>>

    @Query("DELETE FROM movie")
    fun deleteMovies()

    @Query("UPDATE movie SET `like` = :check WHERE id == :id")
    fun updateMovie(id: Int, check: Boolean)

    @Query("SELECT COUNT(*) FROM movie")
    fun countMovies(): Int
}