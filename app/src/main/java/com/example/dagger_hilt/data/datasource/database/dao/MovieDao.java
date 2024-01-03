package com.example.dagger_hilt.data.datasource.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setMovie(MovieEntity movieEntity);

    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntity>> getMovies();

    @Query("DELETE FROM movie")
    void deleteMovies();

    @Query("UPDATE movie SET `like` = :check WHERE id == :id")
    void updateMovie(int id, boolean check);

    @Query("SELECT COUNT(*) FROM movie")
    int countMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setMovies(List<MovieEntity> moviesList);
}
