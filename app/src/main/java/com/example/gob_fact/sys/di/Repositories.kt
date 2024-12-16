package com.example.gob_fact.sys.di

import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.web.api.FactService
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import com.example.gob_fact.domain.IFactRepository
import com.example.gob_fact.sys.util.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Repositories {

    @Provides
    @Singleton
    fun provideFactRepository(
        dao: FactDao,
        service: FactService,
        appExecutor: AppExecutors
    ): IFactRepository = FactRepository(dao, service, appExecutor)

}