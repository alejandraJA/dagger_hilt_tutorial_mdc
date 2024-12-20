package com.example.gob_fact.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.LoadFactsPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    loadFactsPagingUseCase: LoadFactsPagingUseCase
) : ViewModel() {

    val facts: Flow<PagingData<FactEntity>> = loadFactsPagingUseCase()

}