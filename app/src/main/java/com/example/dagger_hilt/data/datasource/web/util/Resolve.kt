package com.example.dagger_hilt.data.datasource.web.util

import retrofit2.Response

const val UNEXPECTED_ERROR: String = "Unexpected error"

class Resolve<T>(
    private val response: Response<T>,
    private val webStatus: WebStatus<T>
) {

    operator fun invoke() {
        if (response.code() == 200) {
            val body = response.body()
            if (body == null) {
                webStatus.error(UNEXPECTED_ERROR)
                return
            }
            webStatus.success(body)
            return
        } else {
            webStatus.error(UNEXPECTED_ERROR)
            return
        }
    }
}