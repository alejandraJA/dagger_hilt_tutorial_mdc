package com.example.dagger_hilt.sys.util

data class ResourceKts<out T>(
    val status: ConstantsKts.StatusResponse,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): ResourceKts<T> =
            ResourceKts(ConstantsKts.StatusResponse.SUCCESS, data, null)

        fun <T> error(msg: String, data: T?): ResourceKts<T> =
            ResourceKts(ConstantsKts.StatusResponse.ERROR, data, msg)

        fun <T> loading(data: T?): ResourceKts<T> =
            ResourceKts(ConstantsKts.StatusResponse.LOADING, data, null)

        fun <T> error(msg: String): ResourceKts<T> =
            ResourceKts(ConstantsKts.StatusResponse.ERROR, null, msg)

        fun <T> loading(): ResourceKts<T> =
            ResourceKts(ConstantsKts.StatusResponse.LOADING, null, null)

        fun <T> blank(): ResourceKts<T> =
            ResourceKts(ConstantsKts.StatusResponse.BLANK, null, null)
    }
}
