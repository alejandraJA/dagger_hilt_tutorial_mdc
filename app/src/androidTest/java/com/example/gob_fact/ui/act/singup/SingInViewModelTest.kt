package com.example.gob_fact.ui.act.singup

import com.example.gob_fact.domain.AuthenticationRepository
import com.example.gob_fact.fake.FakeStorage
import com.example.gob_fact.sys.util.Constants.BIOMETRIC
import com.example.gob_fact.sys.util.Constants.PASSWORD
import com.example.gob_fact.sys.util.Constants.REGISTERED_USER
import com.example.gob_fact.ui.sing.singup.SingInViewModel
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class SingInViewModelTest {

    private lateinit var viewModel: SingInViewModel
    private lateinit var repository: AuthenticationRepository
    private lateinit var fakeStorage: FakeStorage

    @Before
    fun setUp() {
        fakeStorage = FakeStorage()
        repository = AuthenticationRepository(fakeStorage)
        viewModel = SingInViewModel(repository)
    }

    @Test
    fun singIn() {
        val expectedUserName = "test"
        val expectedPassword = "test"
        viewModel.singIn(expectedUserName, expectedPassword)
        val actualUserName = fakeStorage.getString(REGISTERED_USER)
        val actualPassword = fakeStorage.getString("$expectedUserName$PASSWORD")
        assertEquals(expectedUserName, actualUserName)
        assertEquals(expectedPassword, actualPassword)
    }

    @Test
    fun enableBiometric() {
        val expectedValue = true
        viewModel.enableBiometric()
        val actualValue = fakeStorage.getBoolean(BIOMETRIC, true)
        assertEquals(expectedValue, actualValue)
    }
}