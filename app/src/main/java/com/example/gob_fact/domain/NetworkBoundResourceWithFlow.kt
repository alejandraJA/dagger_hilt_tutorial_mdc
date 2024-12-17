package com.example.gob_fact.domain

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.gob_fact.data.datasource.web.models.response.ApiEmptyResponse
import com.example.gob_fact.data.datasource.web.models.response.ApiErrorResponse
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import com.example.gob_fact.data.datasource.web.models.response.ApiSuccessResponse
import com.example.gob_fact.sys.util.AppExecutors
import com.example.gob_fact.sys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

abstract class NetworkBoundResourceWithFlow<ResultType, RequestType> @MainThread constructor(
    appExecutors: AppExecutors
) {

    private val _result = MutableStateFlow<Resource<ResultType>>(Resource.loading(null))
    private val result: StateFlow<Resource<ResultType>> = _result.asStateFlow()

    init {
        appExecutors.networkIO().execute {
            runBlocking {
                val dbSource = loadFromDb().first()
                if (shouldFetch(dbSource)) {
                    fetchFromNetwork(dbSource)
                } else {
                    _result.value = Resource.success(dbSource)
                }
            }
        }
    }

    private suspend fun fetchFromNetwork(dbSource: ResultType?) {
        try {
            when (val apiResponse = createCall().first()) {
                is ApiSuccessResponse -> {
                    saveCallResult(processResponse(apiResponse))
                    val newData = loadFromDb().first()
                    _result.value = Resource.success(newData)
                }

                is ApiEmptyResponse -> {
                    _result.value = Resource.success(dbSource)
                }

                is ApiErrorResponse -> {
                    onFetchFailed()
                    _result.value = Resource.error(apiResponse.errorMessage, dbSource)
                }
            }
        } catch (e: Exception) {
            _result.value = Resource.error(e.message ?: "Unknown error", dbSource)
        }
    }

    protected open fun onFetchFailed() {}

    fun asFlow() = result

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>): RequestType =
        response.body

    @WorkerThread
    protected abstract fun saveCallResult(response: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    abstract fun loadFromDb(): Flow<ResultType>

    @MainThread
    abstract fun createCall(): Flow<ApiResponse<RequestType>>
}