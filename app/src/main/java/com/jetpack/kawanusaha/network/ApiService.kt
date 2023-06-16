package com.jetpack.kawanusaha.network

import com.jetpack.kawanusaha.BuildConfig
import com.jetpack.kawanusaha.data.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Query


interface ApiService {
    // AUTHENTICATION
    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("auth/register")
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
    fun getListUsaha(
        @Header("Authorization") token: String
    ): Call<UsahaResponse>

    @GET("usaha/detail")
    fun getUsahaDetail(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): Call<UsahaDetailResponse>

    @POST("usaha/create")
    fun createUsaha(
        @Header("Authorization") token: String,
        @Body usahaRequest: UsahaRequest,
    ): Call<DefaultResponse>

    @HTTP(method = "DELETE", path = "usaha/delete", hasBody = true)
    fun deleteUsaha(
        @Header("Authorization") token: String,
        @Body id: IdRequest
    ): Call<DefaultResponse>


    // CATEGORY
    @GET("category")
    fun getCategory(): Call<CategoryResponse>

    @GET("article/category")
    suspend fun getCategorizedArticle(
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("category") category: Int
    ): ArticleResponse

    @GET("tag")
    fun getTag(): Call<TagResponse>


    // ALL ARTICLES
    @GET("article")
    suspend fun getAllArticles(
        @Query("page") page: Int,
        @Query("page_size") page_size: Int
    ): ArticleResponse

    @GET("article/search")
    suspend fun searchAllArticles(
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("title") title: String
    ): ArticleResponse

    @GET("article/content")
    fun getArticleDetails(
        @Query("id") id: Int
    ): Call<ArticleDetailResponse>


    // USER ARTICLES
    @GET("article/owned")
    suspend fun getUserArticles(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
    ): ArticleResponse

    @GET("article/owned/search")
    suspend fun searchUserArticle(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("title") title: String
    ): ArticleResponse

    @Multipart
    @POST("article/create")
    fun createArticle(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("article") createArticleRequest: CreateArticleRequest
    ): Call<DefaultResponse>

    @HTTP(method = "DELETE", path = "article/delete", hasBody = true)
    fun deleteArticle(
        @Header("Authorization") token: String,
        @Body id: IdRequest
    ): Call<DefaultResponse>


    // USER PROFILE
    @GET("user/profile")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<ProfileResponse>

    @Multipart
    @PATCH("user/profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part image_changed: String,
        @Part("user") profileRequest: ProfileRequest
    ): Call<DefaultResponse>


    // FAVORITE
    @GET("user/favorite-articles")
    fun getFavourite(
        @Header("Authorization") token: String
    ): Call<FavResponse>

    @POST("article/favorite")
    fun setFavourite(
        @Header("Authorization") token: String,
        @Body id: IdRequest
    ): Call<DefaultResponse>

    @HTTP(method = "DELETE", path = "article/favorite", hasBody = true)
    fun deleteFavourite(
        @Header("Authorization") token: String,
        @Body id: IdRequest
    ): Call<DefaultResponse>


    // CHAT BOT
    @POST("v1/chat/completions")
    fun chatResponse(
        @Header("Authorization") token: String,
        @Body request: LLMRequest
    ): Call<LLMResponse>


    @POST("v1/generate-article")
    fun generateArticle(
        @Header("Authorization") token: String,
        @Body request: LLMRequest
    ): Call<DefaultResponse>


    // FOR INTERNET SEARCH
    @GET("search")
    fun search(
        @Header("X-Subscription-Token") token: String,
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("search_lang") lang: String,
        @Query("safesearch") safesearch: String,
        @Query("result_filter") filter: String,
        @Query("extra_snippets") snippets: String
    ): Call<InternetSearchResponse>
}

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
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

class ApiConfigSearch {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.search.brave.com/res/v1/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}