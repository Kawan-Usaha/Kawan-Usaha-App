package com.jetpack.kawanusaha.data

import android.util.Log
import com.jetpack.kawanusaha.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DataRepository(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return executeRequest { apiService.login(loginRequest).execute() }
    }

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse? {
        return executeRequest { apiService.register(registerRequest).execute() }
    }

    suspend fun generate(): GenerateVerificationResponse? {
        return executeRequest { apiService.generate().execute() }
    }

    suspend fun verify(verificationRequest: VerificationRequest): VerificationResponse? {
        return executeRequest { apiService.verify(verificationRequest).execute() }
    }

    private suspend fun <T> executeRequest(apiCall: suspend () -> Response<T>): T? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e(TAG, "On Failure: ${response.message()}")
                    null
                }
            } catch (t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message}")
                null
            }
        }
    }
    companion object {
        private const val TAG = "DataRepository"
    }
}