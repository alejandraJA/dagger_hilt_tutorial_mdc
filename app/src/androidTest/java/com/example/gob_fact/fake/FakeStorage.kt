package com.example.gob_fact.fake

import com.example.gob_fact.data.datasource.storage.IStorage

class FakeStorage() : IStorage {

    private var fakeSharedPreferences: MutableList<Map<String, Any>> = mutableListOf()

    override fun setString(key: String, value: String) {
        fakeSharedPreferences.add(mapOf(key to value))
    }

    override fun getString(key: String): String {
        var searchValue = ""
        fakeSharedPreferences.forEach {
            if (it.containsKey(key)) {
                searchValue = it[key] as String
            }
        }
        return searchValue
    }

    override fun setBoolean(key: String, value: Boolean) {
        fakeSharedPreferences.add(mapOf(key to value))
    }

    override fun getBoolean(key: String, value: Boolean): Boolean {
        var searchValue = value
        fakeSharedPreferences.forEach {
            if (it.containsKey(key)) {
                searchValue = it[key] as Boolean
            }
        }
        return searchValue
    }

}