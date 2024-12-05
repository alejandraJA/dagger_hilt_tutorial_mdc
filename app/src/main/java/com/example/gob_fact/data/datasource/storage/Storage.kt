package com.example.gob_fact.data.datasource.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.gob_fact.sys.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface IStorage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, value: Boolean): Boolean
}

class Storage @Inject constructor(
    @ApplicationContext private val app: Context
) : IStorage {

    private val sharedPreferences: SharedPreferences =
        app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            commit()
        }
    }

    override fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, value)
    }

}