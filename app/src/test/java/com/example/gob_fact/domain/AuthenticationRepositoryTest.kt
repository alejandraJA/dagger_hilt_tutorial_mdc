package com.example.gob_fact.domain

import com.example.gob_fact.data.datasource.storage.IStorage
import com.example.gob_fact.fake.FakeStorage
import com.example.gob_fact.sys.util.Constants.PASSWORD
import com.example.gob_fact.sys.util.Constants.REGISTERED_USER
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AuthenticationRepositoryTest {

    private lateinit var authenticationRepository: AuthenticationRepository
    private val fakeStorage: IStorage = FakeStorage()

    @Before
    fun setUp() {
        authenticationRepository = AuthenticationRepository(fakeStorage)
    }

    @Test
    fun isEnableBiometric() {
        val expectedValue = true
        authenticationRepository.isEnableBiometric = expectedValue
        val actualValue = authenticationRepository.isEnableBiometric
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun setEnableBiometric() {
        val expectedValue = false
        authenticationRepository.isEnableBiometric = expectedValue
        val actualValue = authenticationRepository.isEnableBiometric
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun registerUser() {
        val expectedUserName = "test"
        val expectedPassword = "test"
        authenticationRepository.registerUser(expectedUserName, expectedPassword)
        val actualUserName = fakeStorage.getString(REGISTERED_USER)
        val actualPassword = fakeStorage.getString("$expectedUserName$PASSWORD")
        assertEquals(expectedUserName, actualUserName)
        assertEquals(expectedPassword, actualPassword)
    }

    @Test
    fun isUserRegistered() {
        val expectedValue = true
        authenticationRepository.registerUser("test", "test")
        val actualValue = authenticationRepository.isUserRegistered()
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun loginUser() {
        val expectedValue = true
        authenticationRepository.registerUser("test", "test")
        val actualValue = authenticationRepository.loginUser("test", "test")
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun unregister() {
        authenticationRepository.registerUser("test", "test")
        authenticationRepository.unregister()
        val actualUserName = fakeStorage.getString("REGISTERED_USER")
        val actualPassword = fakeStorage.getString("testPASSWORD")
        assertEquals("", actualUserName)
        assertEquals("", actualPassword)
    }

}

