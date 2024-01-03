package com.example.dagger_hilt.data.datasource.web.models.response;

public class ApiErrorResponse extends ApiResponse {

    private final String errorMessage;

    public ApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
