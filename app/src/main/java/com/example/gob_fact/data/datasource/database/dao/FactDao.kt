package com.example.gob_fact.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import com.example.gob_fact.data.datasource.database.entities.MovieEntity
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("DELETE FROM movie")
    fun deleteMovies()

    @Query("UPDATE movie SET `like` = :check WHERE id == :id")
    fun updateMovie(id: Int, check: Boolean)

    @Query("SELECT COUNT(*) FROM movie")
    fun countMovies(): Int
}