package com.example.gob_fact.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFact(factEntity: FactEntity)

    @Query("SELECT * FROM fact")
    fun getFacts(): Flow<List<FactEntity>>

    @Query("DELETE FROM fact")
    fun deleteFacts()

    @Query("""
        SELECT * 
        FROM fact 
        WHERE id == :factId 
        ORDER BY fact ASC
        """)
    fun getFact(factId: String): Flow<FactEntity?>

    @Query("""
        SELECT * 
        FROM fact 
        WHERE organization LIKE '%' || :query || '%'
        ORDER BY fact ASC
        """)
    fun searchFact(query: String): Flow<List<FactEntity>>

    @Query("""
        SELECT * 
        FROM fact 
        WHERE organization LIKE '%' || :query || '%'
        LIMIT :pageSize OFFSET :offset
    """)
    fun searchFactPaginated(query: String, pageSize: Int, offset: Int): List<FactEntity>

    @Query("""
        SELECT * 
        FROM fact 
        LIMIT :pageSize OFFSET :offset
    """)
    fun getFactsPaginated(pageSize: Int, offset: Int): List<FactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFacts(facts: List<FactEntity>)
}