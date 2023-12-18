package com.example.dagger_hilt.data.datasource.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntity {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "poster_path")
    private final String posterPath;
    @ColumnInfo(name = "like")
    private final Boolean like;

    public MovieEntity(int id, String title, String originalTitle, String overview, String posterPath, Boolean like) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.like = like;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Boolean getLike() {
        return like;
    }

    public MovieEntityKts toMovieEntityKts(MovieEntity movieEntityKts) {
        return new MovieEntityKts(movieEntityKts.id,
                movieEntityKts.originalTitle,
                movieEntityKts.originalTitle,
                movieEntityKts.overview,
                movieEntityKts.posterPath,
                movieEntityKts.like);
    }
}
