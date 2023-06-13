package com.jetpack.kawanusaha.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jetpack.kawanusaha.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response


class DataRepository(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        return executeRequest { apiService.login(loginRequest).execute() }
    }

    suspend fun register(registerRequest: RegisterRequest): RegisterResponse? {
        return executeRequest { apiService.register(registerRequest).execute() }
    }

    suspend fun generate(jwtToken: String): GenerateVerificationResponse? {
        return executeRequest { apiService.generate("Bearer $jwtToken").execute() }
    }

    suspend fun forgotGenerate(forgotGenerateRequest: ForgotGenerateRequest): GenerateVerificationResponse? {
        return executeRequest { apiService.forgotGenerate(forgotGenerateRequest).execute() }
    }

    suspend fun verify(
        jwtToken: String,
        verificationRequest: VerificationRequest
    ): GenerateVerificationResponse? {
        return executeRequest {
            apiService.verify("Bearer $jwtToken", verificationRequest).execute()
        }
    }

    suspend fun forgotVerify(forgotVerifyRequest: ForgotVerifyRequest): GenerateVerificationResponse? {
        return executeRequest { apiService.forgotVerify(forgotVerifyRequest).execute() }
    }

    suspend fun getUser(jwtToken: String): ProfileResponse? {
        return executeRequest { apiService.getUser("Bearer $jwtToken").execute() }
    }

    suspend fun updateProfile(jwtToken: String, imageMultipart: MultipartBody.Part?, profileRequest: ProfileRequest): DefaultResponse? {
        return executeRequest {
            apiService.updateProfile("Bearer $jwtToken", imageMultipart, profileRequest).execute()
        }
    }

    suspend fun getListUsaha(jwtToken: String): UsahaResponse? {
        return executeRequest { apiService.getListUsaha("Bearer $jwtToken").execute() }
    }

    suspend fun getUsahaDetail(jwtToken: String, id: Int): UsahaDetailResponse? {
        return executeRequest { apiService.getUsahaDetail("Bearer $jwtToken", id).execute() }
    }

    suspend fun createUsaha(jwtToken: String, usahaRequest: UsahaRequest): DefaultResponse? {
        return executeRequest { apiService.createUsaha("Bearer $jwtToken", usahaRequest).execute() }
    }

    // Category
    suspend fun getCategory(): CategoryResponse?{
        return executeRequest { apiService.getCategory().execute() }
    }


    // Article
    fun getListArticle(): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 6, initialLoadSize = 6),
            pagingSourceFactory = { ArticlePagingSource(apiService, null, null) }
        ).flow
    }

    fun getUserArticle(jwtToken: String): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 6, initialLoadSize = 6),
            pagingSourceFactory = { ArticlePagingSource(apiService, "Bearer $jwtToken", null) }
        ).flow
    }

    fun searchAllArticle(text: String): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 6, initialLoadSize = 6),
            pagingSourceFactory = { ArticlePagingSource(apiService, null, text) }
        ).flow
    }

    fun getCategorizedArticles (id : Int): Flow<PagingData<ArticlesItem>>{
        return Pager(
            config = PagingConfig(pageSize = 6, initialLoadSize = 6),
            pagingSourceFactory = { CategorizedPagingSource(apiService, id) }
        ).flow
    }

    suspend fun setFavourite (jwtToken: String, id: Int): DefaultResponse?{
        return executeRequest { apiService.setFavourite("Bearer $jwtToken", id).execute() }
    }

    suspend fun getFavourite (jwtToken: String): FavResponse? {
        return executeRequest { apiService.getFavourite("Bearer: $jwtToken").execute() }
    }

    suspend fun getArticleDetail(id: Int): ArticleDetailResponse? {
        return executeRequest { apiService.getArticleDetails(id).execute() }
    }

    suspend fun createArticle(
        jwtToken: String,
        imageMultipart: MultipartBody.Part?,
        createArticleRequest: CreateArticleRequest
    ): DefaultResponse? {
        return executeRequest {
            apiService.createArticle("Bearer $jwtToken", imageMultipart , createArticleRequest).execute()
        }
    }

    suspend fun chatResult(jwtToken: String, llmRequest: LLMRequest): LLMResponse? {
        return executeRequest { apiService.chatResponse("Bearer $jwtToken", llmRequest).execute() }
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