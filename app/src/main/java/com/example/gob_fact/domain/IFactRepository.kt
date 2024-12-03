package com.example.gob_fact.domain

import androidx.lifecycle.LiveData
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.database.entities.MovieEntity
import com.example.gob_fact.sys.util.Resource

interface IFactRepository {
    fun loadMovies(): LiveData<Resource<List<MovieEntity>>>
    fun updateMovie(id: Int, check: Boolean)
    fun loadFacts(): LiveData<Resource<List<FactEntity>>>
    fun deleteFacts()
    fun getFacts(): LiveData<List<FactEntity>>
}