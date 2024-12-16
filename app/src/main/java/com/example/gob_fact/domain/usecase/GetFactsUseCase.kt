package com.example.gob_fact.domain.usecase

import com.example.gob_fact.domain.IFactRepository
import javax.inject.Inject

class GetFactsUseCase @Inject constructor(private val factRepository: IFactRepository)
{

}