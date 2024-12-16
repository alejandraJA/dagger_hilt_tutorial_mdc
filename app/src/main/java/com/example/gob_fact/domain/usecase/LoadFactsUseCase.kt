package com.example.gob_fact.domain.usecase

import com.example.gob_fact.domain.repository.IFactRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadFactsUseCase @Inject constructor(
    private val factRepository: IFactRepository
) {
    operator fun invoke() = factRepository.loadFacts()
}

