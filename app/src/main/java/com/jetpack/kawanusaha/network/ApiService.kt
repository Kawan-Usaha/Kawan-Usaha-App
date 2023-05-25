package com.jetpack.kawanusaha.network

import com.jetpack.kawanusaha.BuildConfig
import com.jetpack.kawanusaha.data.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    // AUTHENTICATION
    @POST ("auth/login")
    fun login (
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST ("auth/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>


    // USER VERIFICATION
    @POST("auth/generate")
    fun generate(
        @Header("Authorization") token: String,
    ): Call<GenerateVerificationResponse>

    @POST("auth/verify")
    fun verify(
        @Header("Authorization") token: String,
        @Body verificationRequest: VerificationRequest
    ): Call<GenerateVerificationResponse>


    // FORGOT PASSWORD
    @POST("auth/forgot-password/generate")
    fun forgotGenerate(
        @Body forgotGenerateRequest: ForgotGenerateRequest
    ): Call<GenerateVerificationResponse>

    @POST("auth/forgot-password/verify")
    fun forgotVerify(
        @Body forgotVerifyRequest: ForgotVerifyRequest
    ): Call<GenerateVerificationResponse>


    // USAHA
    @GET("usaha/list")
    fun getListUsaha (
        @Header("Authorization") token: String,
    ) //: Call <Response>

    @GET("usaha/search")
    fun searchUsaha (
        @Header("Authorization") token: String,
        @Query("name") name: String,
    ) //: Call <Response>

    @GET("usaha/detail")
    fun getUsahaDetail (
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ) //: Call <Response>

    @POST("usaha/create")
    fun createUsaha (
        @Header("Authorization") token: String,
        @Body usahaRequest : UsahaRequest,
    ) //: Call <Response>


    // ALL ARTICLES
    @GET("article")
    fun getAllArticles (
        @Query ("page") page: Int,
        @Query ("page_size") page_size: Int
    ) //: Call <Response>

    @GET("article/search")
    fun searchAllArticles (
        @Query ("page") page: Int,
        @Query ("page_size") page_size: Int,
        @Query ("title") title: String
    ) //: Call <Response>


    // USER ARTICLES
    @GET("article/owned")
    fun getUserArticles (
        @Header("Authorization") token: String,
    ) //: Call <Response>

    @GET("article/search")
    fun searchUserArticle (
        @Header("Authorization") token: String,
        // Mungkin masi diiupdate
    ) //: Call <Response>

    @POST("article/create")
    fun createUserArticle (
        @Header("Authorization") token: String,
        @Body articleRequest : ArticleRequest,
    ) //: Call <Response>


    // USER PROFILE
    @GET("user/profile")
    fun getUser (
        @Header("Authorization") token: String
    ): Call<ProfileResponse>
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
                .baseUrl("http://34.170.84.70:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}