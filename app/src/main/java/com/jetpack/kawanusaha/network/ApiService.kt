package com.jetpack.kawanusaha.network

import android.net.Uri
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.jetpack.kawanusaha.BuildConfig
import com.jetpack.kawanusaha.data.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.sse.EventSources
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


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
        @Header("Authorization") token: String
    ) : Call <UsahaResponse>

    @GET("usaha/search")
    fun searchUsaha (
        @Header("Authorization") token: String,
        @Query("name") name: String,
    ) //: Call <Response>

    @GET("usaha/detail")
    fun getUsahaDetail (
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ) : Call<UsahaDetailResponse>

    @POST("usaha/create")
    fun createUsaha (
        @Header("Authorization") token: String,
        @Body usahaRequest : UsahaRequest,
    ) : Call <DefaultResponse>


    // CATEGORY
    @GET("category")
    fun getCategory () : Call<CategoryResponse>

    @GET("article/category")
    suspend fun getCategorizedArticle (
        @Query ("page") page : Int,
        @Query ("page_size") page_size: Int,
        @Query ("category") category: Int
    ) : ArticleResponse


    // ALL ARTICLES
    @GET("article")
    suspend fun getAllArticles (
        @Query ("page") page: Int,
        @Query ("page_size") page_size: Int
    ) : ArticleResponse

    @GET("article/search")
    suspend fun searchAllArticles (
        @Query ("page") page: Int,
        @Query ("page_size") page_size: Int,
        @Query ("title") title: String
    ) : ArticleResponse

    @GET("article/content")
    fun getArticleDetails (
        @Query ("id") id : Int
    ) : Call<ArticleDetailResponse>


    // USER ARTICLES
    @GET("article/owned")
    suspend fun getUserArticles (
        @Header("Authorization") token: String,
        @Query ("page") page: Int,
        @Query ("page_size") page_size: Int,
    ) : ArticleResponse

    @GET("article/owned/search")
    suspend fun searchUserArticle (
        @Header("Authorization") token: String,
        @Query ("page") page: Int,
        @Query ("page_size") page_size: Int,
        @Query ("title") title: String
    ): ArticleResponse

    @Multipart
    @POST("article/create")
    fun createArticle (
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("article") createArticleRequest: CreateArticleRequest
    ) : Call<DefaultResponse>


    // USER PROFILE
    @GET("user/profile")
    fun getUser (
        @Header("Authorization") token: String
    ): Call<ProfileResponse>

    @Multipart
    @PATCH("user/profile")
    fun updateProfile (
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("user") profileRequest: ProfileRequest
    ): Call<DefaultResponse>

    // FAVORITE
    @GET("user/favorite-articles")
    fun getFavourite(
        @Header("Authorization") token: String
    ): Call<FavResponse>

    @POST("/article/favorite")
    fun setFavourite(
        @Header("Authorization") token: String,
        @Body id : IdRequest
    ): Call<DefaultResponse>

    // CHAT BOT
    @POST("v1/chat/completions")
    fun chatResponse (
        @Header("Authorization") token: String,
        @Body request : LLMRequest
    ): Call<LLMResponse>
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
                .baseUrl("https://api.kawan-usaha.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}