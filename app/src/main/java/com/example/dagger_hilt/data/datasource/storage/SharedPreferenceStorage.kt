package com.example.dagger_hilt.data.datasource.storage

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

}