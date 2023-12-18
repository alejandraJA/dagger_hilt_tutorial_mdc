package com.example.dagger_hilt.data.db

import androidx.room.Database
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import androidx.room.RoomDatabase
import com.example.dagger_hilt.data.datasource.database.dao.MovieDaoKts

@Database(entities = [MovieEntityKts::class], version = 1, exportSchema = true)
abstract class AppDatabaseKts : RoomDatabase() {
    abstract fun quoteDao(): MovieDaoKts
}