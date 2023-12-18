package com.example.dagger_hilt.data.datasource.web.util

import retrofit2.Response

const val UNEXPECTED_ERROR: String = "Unexpected error"

class Resolve<T>(
    private val response: Response<T>,
    private val webStatusKts: WebStatusKts<T>
) {

    operator fun invoke() {
        if (response.code() == 200) {
            val body = response.body()
            if (body == null) {
                webStatusKts.error(UNEXPECTED_ERROR)
                return
            }
            webStatusKts.success(body)
            return
        } else {
            webStatusKts.error(UNEXPECTED_ERROR)
            return
        }
    }
}