package com.example.dagger_hilt.sys.util

import com.example.dagger_hilt.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL_IMEGES = BuildConfig.BASE_URL_IMEGES
    enum class StatusResponse { SUCCESS, ERROR, LOADING, BLANK }
    const val USER_MEMORY = "user"
    const val REGISTERED_USER = "registeredUser"
    const val PASSWORD = "password"
}