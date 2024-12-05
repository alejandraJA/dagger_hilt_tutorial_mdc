package com.example.gob_fact.utils

import androidx.lifecycle.MutableLiveData
import com.example.gob_fact.data.datasource.database.entities.FactEntity

fun <T> List<T>.toLiveData(): MutableLiveData<List<T>> {
    return MutableLiveData(this)
}

fun <T> T.toLiveData(): MutableLiveData<T> {
    return MutableLiveData(this)
}
