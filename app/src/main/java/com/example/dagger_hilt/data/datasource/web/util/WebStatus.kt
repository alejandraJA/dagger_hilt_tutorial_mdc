package com.example.dagger_hilt.data.datasource.web.util

interface WebStatus<T> {
    fun success(data: T)
    fun error(e: String)
}