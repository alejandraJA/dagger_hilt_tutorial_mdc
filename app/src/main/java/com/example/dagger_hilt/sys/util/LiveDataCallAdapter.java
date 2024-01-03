package com.example.dagger_hilt.sys.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse>> {

    private final Type bodyType;

    public LiveDataCallAdapter(Type bodyType) {
        this.bodyType = bodyType;
    }

    @NonNull
    @Override
    public Type responseType() {
        return bodyType;
    }

    @NonNull
    @Override
    public LiveData<ApiResponse> adapt(@NonNull Call<R> call) {
        return new LiveData<>() {
            private final AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                            postValue(ApiResponse.create(response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<R> call, @NonNull Throwable t) {
                            postValue(ApiResponse.create(t));
                        }
                    });
                }
            }
        };
    }
}
