package com.example.gob_fact.ui.act.login

import com.example.gob_fact.domain.AuthenticationRepository
import com.example.gob_fact.fake.FakeStorage
import com.example.gob_fact.sys.util.Constants
import com.example.gob_fact.ui.sing.login.LoginViewModel
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthenticationRepository
    private lateinit var fakeStorage: FakeStorage

    @Before
    fun setUp() {
        fakeStorage = FakeStorage()
        repository = AuthenticationRepository(fakeStorage)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun isBiometricDisabled() {
        val expectedValue = true
        repository.isEnableBiometric = expectedValue
        val actualValue = !viewModel.isBiometricDisabled
        assertEquals(expectedValue, actualValue)
        assertNotEquals(expectedValue, viewModel.isBiometricDisabled)
    }

    @Test
    fun getUserName() {
        val value = "test"
        val secondValue = "test1"
        repository.registerUser(value, value)
        val actualValue = fakeStorage.getString(Constants.REGISTERED_USER)
        assertEquals(value, actualValue)
        assertNotEquals(secondValue, viewModel.userName.value)
    }

    @Test
    fun login() {
        val expectedValue = true
        repository.registerUser("test", "test")
        val actualValue = viewModel.login("test", "test")
        assertEquals(expectedValue, actualValue)
    }
}