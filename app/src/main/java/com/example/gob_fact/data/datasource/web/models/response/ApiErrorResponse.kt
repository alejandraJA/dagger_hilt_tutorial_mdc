package com.example.gob_fact.data.datasource.web.models.response

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()