package com.example.dagger_hilt.data.datasource.web.models.response;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public abstract class ApiResponse {
    public static ApiErrorResponse create(Throwable error) {
        return new ApiErrorResponse(error.getMessage() != null ? error.getMessage() : "unknown error");
    }

    public static <T> ApiResponse create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();
            boolean isEmptyList = body instanceof List<?> && ((java.util.List<?>) body).isEmpty();
            if (body == null || response.code() == 204 || isEmptyList) {
                return new ApiEmptyResponse();
            } else {
                return new ApiSuccessResponse<>(body, response.headers().get("link"));
            }
        } else {
            @Nullable String msg = null;
            try {
                assert response.errorBody() != null;
                msg = response.errorBody().string();
            } catch (Exception e) {
                Timber.e(e, "Error parsing errorBody");
            }
            String errorMsg = (msg == null || msg.isEmpty()) ? response.message() : msg;
            return new ApiErrorResponse(errorMsg);
        }
    }

    public static <T> MutableLiveData<ApiResponse> create(Call<T> call) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    T body = response.body();
                    boolean isEmptyList = body instanceof List<?> && ((java.util.List<?>) body).isEmpty();
                    if (body == null || response.code() == 204 || isEmptyList) {
                        liveData.postValue(new ApiEmptyResponse());
                    } else {
                        liveData.postValue(new ApiSuccessResponse<>(body,
                                response.headers().get("link")));
                    }
                } else {
                    @Nullable String msg = null;
                    try {
                        assert response.errorBody() != null;
                        msg = response.errorBody().string();
                    } catch (Exception e) {
                        Timber.e(e, "Error parsing errorBody");
                    }
                    String errorMsg = (msg == null || msg.isEmpty()) ? response.message() : msg;
                    liveData.postValue(new ApiErrorResponse(errorMsg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                liveData.postValue(new ApiErrorResponse(t.getMessage()));
            }
        });
        return liveData;
    }
}