package com.jetpack.kawanusaha.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jetpack.kawanusaha.utility.*
import com.jetpack.kawanusaha.data.DataRepository
import com.jetpack.kawanusaha.data.LoginRequest
import com.jetpack.kawanusaha.data.RegisterRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(dataRepository)
    }

    @Test
    fun login() = runTest {
        val expectedResult = DataDummy.generateLoginDummy()
        `when`(dataRepository.login(LoginRequest("email", "password"))).thenReturn(expectedResult)
        loginViewModel.login("email", "password")
        val actualResult = loginViewModel.loginCredential.value

        // Data is not null
        Assert.assertNotNull(actualResult)
        // Actual data is the same as Expected data
        Assert.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun register() = runTest {
        val expectedResult = DataDummy.generateRegisterDummy()
        `when`(dataRepository.register(RegisterRequest("name", "email", "password", "password"))).thenReturn(expectedResult)
        loginViewModel.register("name", "email", "password" ,"password")
        val actualResult = loginViewModel.registerCredential.value

        // Data is not null
        Assert.assertNotNull(actualResult)
        // Actual data is the same as Expected data
        Assert.assertEquals(expectedResult, actualResult)
    }
}