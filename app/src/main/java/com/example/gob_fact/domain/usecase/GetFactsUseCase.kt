package com.example.gob_fact.domain.usecase

import com.example.gob_fact.domain.repository.IFactRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFactsUseCase @Inject constructor(
    private val factRepository: IFactRepository
) {
    operator fun invoke(query: String?) =
        if (query == null) factRepository.getFacts()
        else factRepository.searchFact(query.trim())
}

