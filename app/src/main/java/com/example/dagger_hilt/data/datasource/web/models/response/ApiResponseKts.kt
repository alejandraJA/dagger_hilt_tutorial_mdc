package com.example.dagger_hilt.data.datasource.web.models.response

import retrofit2.Response

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponseKts<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponseKts<T> {
            return ApiErrorResponseKts(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponseKts<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                val emptyList = (body is List<*> && (body as List<*>).isEmpty())
                if (body == null || response.code() == 204 || emptyList) {
                    ApiEmptyResponseKts()
                } else {
                    ApiSuccessResponseKts(
                        body = body,
                        linkHeader = response.headers()["link"]
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponseKts(errorMsg ?: "unknown error")
            }
        }
    }
}