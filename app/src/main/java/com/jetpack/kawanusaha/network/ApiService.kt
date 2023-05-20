package com.jetpack.kawanusaha.network

import com.jetpack.kawanusaha.BuildConfig
import com.jetpack.kawanusaha.data.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST ("auth/login")
    fun login (
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST ("auth/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>
}

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://localhost:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
            //TODO ganti baseUrl
        }
    }
}