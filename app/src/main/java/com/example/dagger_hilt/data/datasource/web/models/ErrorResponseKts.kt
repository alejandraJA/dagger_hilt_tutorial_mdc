package com.example.dagger_hilt.data.datasource.web.models

data class ErrorResponseKts(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)