package com.example.gob_fact.data.datasource.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact")
data class FactEntity (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val columns: String,
    val createdAt: Int,
    val dataset: String,
    val dateInsert: String,
    val fact: String,
    val operations: String,
    val organization: String,
    val resource: String,
    val slug: String,
    val url: String
)