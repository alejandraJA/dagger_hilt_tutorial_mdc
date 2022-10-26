package com.example.dagger_hilt.data.db

import androidx.room.Database
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity
import androidx.room.RoomDatabase
import com.example.dagger_hilt.data.datasource.database.dao.MovieDao

@Database(entities = [MovieEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): MovieDao
}