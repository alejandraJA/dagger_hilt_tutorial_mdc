package com.example.dagger_hilt.domain;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.dagger_hilt.data.datasource.web.models.response.ApiEmptyResponse;
import com.example.dagger_hilt.data.datasource.web.models.response.ApiErrorResponse;
import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponse;
import com.example.dagger_hilt.data.datasource.web.models.response.ApiSuccessResponse;
import com.example.dagger_hilt.sys.util.AppExecutors;
import com.example.dagger_hilt.sys.util.Resource;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        setValue(Resource.loading(null));
        var dbSource = loadFromDb();
        result.addSource(dbSource, (data) -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, (newData) -> setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse> apiResponse;
        apiResponse = createCall();

        result.addSource(dbSource, (newData -> setValue(Resource.loading(newData))));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if (response instanceof ApiSuccessResponse<?>)
                appExecutors.getDiskIO().execute(() -> {
                            saveCallResult(processResponse((ApiSuccessResponse<RequestType>) response));
                            appExecutors.getMainThread().execute(() ->
                                    result.addSource(loadFromDb(), (newData) ->
                                            setValue(Resource.success(newData))));
                        }
                );
            else if (response instanceof ApiEmptyResponse)
                appExecutors.getMainThread().execute(() ->
                        result.addSource(loadFromDb(), (newData) ->
                                setValue(Resource.success(newData))));
            else if (response instanceof ApiErrorResponse) {
                onFetchFailed();
                result.addSource(dbSource, (newData ->
                        setValue(Resource.error(((ApiErrorResponse) response)
                                .getErrorMessage(), newData))));
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    protected void onFetchFailed() {

    }

    @WorkerThread
    protected abstract void saveCallResult(RequestType requestType);

    @WorkerThread
    protected RequestType processResponse(ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }

    @MainThread
    protected abstract LiveData<ApiResponse> createCall();

    @MainThread
    protected abstract boolean shouldFetch(ResultType data);

    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

}
