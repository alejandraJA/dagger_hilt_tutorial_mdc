package com.example.dagger_hilt.data.datasource.web.models.response

data class ApiErrorResponseKts<T>(val errorMessage: String) : ApiResponseKts<T>()