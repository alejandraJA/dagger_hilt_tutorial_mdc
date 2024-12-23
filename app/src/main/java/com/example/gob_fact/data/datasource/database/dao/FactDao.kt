package com.example.gob_fact.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Query("SELECT * FROM fact")
    fun getFacts(): Flow<List<FactEntity>>

    @Query("SELECT * FROM fact")
    fun getAllFacts(): List<FactEntity>?

    @Query("""
        SELECT * 
        FROM fact
        WHERE organization LIKE '%' || :organization || '%'
        """)
    fun getAllFacts(organization: String): List<FactEntity>?

    @Query("""
        SELECT * 
        FROM fact 
        WHERE id == :factId 
        ORDER BY fact ASC
        """)
    fun getFact(factId: String): Flow<FactEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacts(facts: List<FactEntity>)

    @Query("SELECT COUNT(fact) from fact")
    fun countFacts(): Flow<Int>
    @Query("DELETE FROM fact")
    fun clear()

}