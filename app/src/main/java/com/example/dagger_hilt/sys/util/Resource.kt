package com.example.dagger_hilt.sys.util

data class Resource<out T>(
    val status: Constants.StatusResponse,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> =
            Resource(Constants.StatusResponse.SUCCESS, data, null)

        fun <T> error(msg: String): Resource<T> =
            Resource(Constants.StatusResponse.ERROR, null, msg)

        fun <T> loading(): Resource<T> = Resource(Constants.StatusResponse.LOADING, null, null)

        fun <T> blank(): Resource<T> = Resource(Constants.StatusResponse.BLANK, null, null)
    }
}
