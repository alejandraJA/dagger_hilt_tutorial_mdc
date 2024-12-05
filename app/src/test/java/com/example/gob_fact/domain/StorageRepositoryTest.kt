package com.example.gob_fact.domain

import com.example.gob_fact.data.datasource.storage.IStorage
import com.example.gob_fact.fake.FakeStorage
import com.example.gob_fact.sys.util.Constants.PASSWORD
import com.example.gob_fact.sys.util.Constants.REGISTERED_USER
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class StorageRepositoryTest {

    private lateinit var storageRepository: StorageRepository
    private val fakeStorage: IStorage = FakeStorage()

    @Before
    fun setUp() {
        storageRepository = StorageRepository(fakeStorage)
    }

    @Test
    fun isEnableBiometric() {
        val expectedValue = true
        storageRepository.isEnableBiometric = expectedValue
        val actualValue = storageRepository.isEnableBiometric
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun setEnableBiometric() {
        val expectedValue = false
        storageRepository.isEnableBiometric = expectedValue
        val actualValue = storageRepository.isEnableBiometric
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun registerUser() {
        val expectedUserName = "test"
        val expectedPassword = "test"
        storageRepository.registerUser(expectedUserName, expectedPassword)
        val actualUserName = fakeStorage.getString(REGISTERED_USER)
        val actualPassword = fakeStorage.getString("$expectedUserName$PASSWORD")
        assertEquals(expectedUserName, actualUserName)
        assertEquals(expectedPassword, actualPassword)
    }

    @Test
    fun isUserRegistered() {
        val expectedValue = true
        storageRepository.registerUser("test", "test")
        val actualValue = storageRepository.isUserRegistered()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun loginUser() {
        val expectedValue = true
        storageRepository.registerUser("test", "test")
        val actualValue = storageRepository.loginUser("test", "test")
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun unregister() {
        storageRepository.registerUser("test", "test")
        storageRepository.unregister()
        val actualUserName = fakeStorage.getString("REGISTERED_USER")
        val actualPassword = fakeStorage.getString("testPASSWORD")
        assertEquals("", actualUserName)
        assertEquals("", actualPassword)
    }

}

