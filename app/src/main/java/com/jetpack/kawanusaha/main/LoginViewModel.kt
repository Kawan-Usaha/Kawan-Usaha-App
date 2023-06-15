package com.jetpack.kawanusaha.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * *Authentication Functions*
 *
 * This class every logics to authenticate users to the application
 *
 * @property dataRepository the repository of all data
 * @property preferences the shared preference to keep local data
 */
class LoginViewModel(
    private val dataRepository: DataRepository,
    private val preferences: SharedPreferences
) : ViewModel() {
    private val _loginCredential = MutableStateFlow<LoginResponse?>(null)
    val loginCredential: StateFlow<LoginResponse?> = _loginCredential

    private val _registerCredential = MutableStateFlow<RegisterResponse?>(null)
    val registerCredential: StateFlow<RegisterResponse?> = _registerCredential

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    private val _isVerified = MutableStateFlow(false)
    val isVerified: StateFlow<Boolean> = _isVerified

    init {
        clear()
    }

    fun clear() {
        _isVerified.value = false
    }

    /**
     *  Authenticate user and check for its credentials
     *  User credential will be saved in loginCredential variable
     *  Session Created
     *
     *  @param email user email address
     *  @param password user password
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginCredential.value = dataRepository.login(
                loginRequest = LoginRequest(
                    email = email.lowercase(),
                    password = password
                )
            )
            if (loginCredential.value != null) {
                createSession(loginCredential.value?.data?.token.toString())
            }
        }
    }

    /**
     *  Create a new account for new user.
     *  User credential will be saved in registerCredential variable
     *  Session Created
     *
     *  @param name user name
     *  @param email user email address
     *  @param password user password
     *  @param passwordConfirm user password to avoid mistype
     */
    fun register(name: String, email: String, password: String, passwordConfirm: String) {
        viewModelScope.launch {
            _registerCredential.value = dataRepository.register(
                registerRequest = RegisterRequest(
                    name = name,
                    email = email.lowercase(),
                    password = password,
                    password_confirm = passwordConfirm
                )
            )
            if (registerCredential.value != null) {
                createSession(registerCredential.value?.data?.token.toString())
            }
        }
    }

    /**
     *  Create a new session by saving token into shared preferences
     *
     *  @param token Jwt Token
     */
    private fun createSession(token: String) {
        preferences.edit().putString(TOKEN, token).apply()
    }

    /**
     * Check the login status
     *
     * @return login status
     */
    fun isLoggedIn(): Boolean {
        return preferences.getString(TOKEN, null) != null
    }

    /**
     * Clear session and token from preferences
     */
    fun logout() {
        preferences.edit().clear().apply()
    }

    /**
     *  Generate a token and send it to user email address
     *
     *  @param email user email address
     */
    fun generate(email: String?) {
        viewModelScope.launch {
            if (email == null) {
                dataRepository.generate(preferences.getString(TOKEN, "").toString()).let {
                    _message.value = it?.message.toString()
                }
            } else {
                dataRepository.forgotGenerate(forgotGenerateRequest = ForgotGenerateRequest(email = email))
                    .let {
                        _message.value = it?.message.toString()
                    }
            }

        }
    }

    /**
     *  Verify the token inputted by user
     *
     *  @param verification_code the code verification
     *  @param password user password
     *  @param passwordConfirm user password to avoid mistype
     */
    fun verify(verification_code: String, password: String?, passwordConfirm: String?) {
        viewModelScope.launch {
            if (password == null && passwordConfirm == null) {
                dataRepository.verify(
                    preferences.getString(TOKEN, "").toString(),
                    VerificationRequest(verification_code)
                ).let {
                    _isVerified.value = (it?.success ?: false) &&
                            (it?.message!! == "Email verified")
                    _message.value = it?.message.toString()
                }
            } else {
                dataRepository.forgotVerify(
                    forgotVerifyRequest = ForgotVerifyRequest(
                        verification_code = verification_code,
                        password = password!!,
                        password_confirm = passwordConfirm!!
                    )
                ).let {
                    _isVerified.value = (it?.success == true) &&
                            (it.message == "Password changed successfully")
                    _message.value = it?.message.toString()
                }
            }
        }
    }

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    companion object {
        private const val TOKEN = "TOKEN"
    }
}

/**
 * Factory class for creating instances of LoginViewModel.
 * @property preferences The instance of SharedPreferences used for storing login preferences.
 */
class LoginViewModelFactory(
    private val preferences: SharedPreferences
) : ViewModelProvider.Factory {
    /**
     * Creates an instance of the specified ViewModel class.
     * @param modelClass The class of the ViewModel to be created.
     * @return An instance of the ViewModel.
     * @throws IllegalArgumentException if the specified ViewModel class is unknown.
     * */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(Injection.provideRepository(), preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}