package com.example.dagger_hilt.sys.util;

public class Resource<T> {
    private Constants.StatusResponse status;
    private T data;
    private String message;

    public Resource(Constants.StatusResponse status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Constants.StatusResponse getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Constants.StatusResponse.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg) {
        return new Resource<>(Constants.StatusResponse.ERROR, null, msg);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Constants.StatusResponse.LOADING, null, null);
    }

    public static <T> Resource<T> blank() {
        return new Resource<>(Constants.StatusResponse.BLANK, null, null);
    }
}

