package com.example.dagger_hilt.domain

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dagger_hilt.data.datasource.web.models.response.ApiEmptyResponseKts
import com.example.dagger_hilt.data.datasource.web.models.response.ApiErrorResponseKts
import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponseKts
import com.example.dagger_hilt.data.datasource.web.models.response.ApiSuccessResponseKts
import com.example.dagger_hilt.sys.util.AppExecutorsKts
import com.example.dagger_hilt.sys.util.ResourceKts as Resource

abstract class NetworkBoundResourceKts<ResultType, RequestType> @MainThread constructor(
    private val appExecutorsKts: AppExecutorsKts
) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data))
                fetchFromNetwork(dbSource)
            else result.addSource(dbSource) { newData ->
                setValue(Resource.success(newData))
            }

        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponseKts: LiveData<ApiResponseKts<RequestType>> = createCall()
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponseKts) { response ->
            result.removeSource(apiResponseKts)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponseKts -> {
                    appExecutorsKts.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutorsKts.mainThread().execute {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }

                is ApiEmptyResponseKts -> {
                    appExecutorsKts.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }

                is ApiErrorResponseKts -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponseKts<RequestType>): RequestType =
        response.body

    @WorkerThread
    protected abstract fun saveCallResult(response: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponseKts<RequestType>>
}
