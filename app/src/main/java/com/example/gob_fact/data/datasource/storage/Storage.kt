package com.example.gob_fact.data.datasource.storage

import android.content.Context
import com.example.gob_fact.sys.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Storage @Inject constructor(
    @ApplicationContext private val app: Context
) {

    private val sharedPreferences =
        app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE)

    fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

}