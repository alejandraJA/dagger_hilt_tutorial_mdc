package com.example.gob_fact.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.example.gob_fact.data.datasource.database.entities.FactEntity

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFact(factEntity: FactEntity)

    @Query("SELECT * FROM fact")
    fun getFacts(): LiveData<List<FactEntity>>

    @Query("DELETE FROM fact")
    fun deleteFacts()

    @Query("SELECT * FROM fact WHERE id == :factId")
    fun getFact(factId: String): LiveData<FactEntity?>

    @Query("""
        SELECT * 
        FROM fact 
        WHERE organization LIKE '%' || :query || '%'
        """)
    fun searchFact(query: String): LiveData<List<FactEntity>>
}