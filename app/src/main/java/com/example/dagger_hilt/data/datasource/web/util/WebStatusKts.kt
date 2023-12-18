package com.example.dagger_hilt.data.datasource.web.util

interface WebStatusKts<T> {
    fun success(data: T)
    fun error(e: String)
}