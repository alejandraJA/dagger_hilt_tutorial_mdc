package com.example.dagger_hilt.sys.util;

import androidx.annotation.Nullable;

public record Resource<T>(Constants.StatusResponse status, @Nullable T data, String message) {

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Constants.StatusResponse.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Constants.StatusResponse.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Constants.StatusResponse.LOADING, data, null);
    }
}

