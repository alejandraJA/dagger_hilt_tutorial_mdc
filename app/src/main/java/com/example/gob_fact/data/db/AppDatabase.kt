package com.example.gob_fact.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.database.entities.FactEntity

@Database(entities = [FactEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}