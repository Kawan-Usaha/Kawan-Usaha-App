package com.jetpack.kawanusaha.main

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.utility.*
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
    @Mock
    private lateinit var preferences: SharedPreferences
    private val token: String = "token"

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(dataRepository, preferences)
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

    @Test
    fun `when generate validation key from register` () = runTest {
        // This test is using auth/generate route
        val expectedResult = DataDummy.generateGenerateDummy()
        `when`(dataRepository.generate(token)).thenReturn(expectedResult)
        loginViewModel.generate(null)
        val actualResult = loginViewModel.message.value

        // Data is not null
        Assert.assertNotNull(actualResult)
        // Actual data message is the same as Expected data
        Assert.assertEquals(expectedResult.message, actualResult)
    }

    @Test
    fun `when generate validation key from forgot password` () = runTest {
        // This test is using auth/forgot-password/generate route
        val expectedResult = DataDummy.generateGenerateDummy()
        `when`(dataRepository.forgotGenerate(ForgotGenerateRequest("email"))).thenReturn(expectedResult)
        loginViewModel.generate("email")
        val actualResult = loginViewModel.message.value

        // Data is not null
        Assert.assertNotNull(actualResult)
        // Actual data message is the same as Expected data
        Assert.assertEquals(expectedResult.message, actualResult)
    }

     @Test
     fun `when verify validation key from register` () = runTest{
         // This test is using auth/verify route
         val expectedResult = DataDummy.generateGenerateDummy()
         val verificationRequest = VerificationRequest("ABCDEF")
         `when`(dataRepository.verify(token, verificationRequest)).thenReturn(expectedResult)
         loginViewModel.verify("ABCDEF", null, null)
         val actualResult = loginViewModel.message.value

         // Data is not null
         Assert.assertNotNull(actualResult)

         // Actual data message is the same as Expected data
         Assert.assertEquals(expectedResult.message, actualResult)
     }

    @Test
    fun `when verify validation key from forgot password` () = runTest{
        // This test is using auth/forgot-password/verify route
        val expectedResult = DataDummy.generateGenerateDummy()
        val verificationRequest = ForgotVerifyRequest(verification_code = "ABCDEF", password = "password", password_confirm =  "password")
        `when`(dataRepository.forgotVerify(verificationRequest)).thenReturn(expectedResult)
        loginViewModel.verify("ABCDEF", "password", "password")
        val actualResult = loginViewModel.message.value

        // Data is not null
        Assert.assertNotNull(actualResult)

        // Actual data message is the same as Expected data
        Assert.assertEquals(expectedResult.message, actualResult)
    }
}