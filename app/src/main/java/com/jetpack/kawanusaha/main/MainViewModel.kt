package com.jetpack.kawanusaha.main

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.db.DbData
import com.jetpack.kawanusaha.db.DbRepository
import com.jetpack.kawanusaha.`in`.Injection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainViewModel(
    private val dataRepository: DataRepository,
    private val preferences: SharedPreferences,
    private val localRepository: DbRepository
) : ViewModel() {
    private val _userProfile = MutableStateFlow<ProfileResponse?>(null)
    val userProfile: StateFlow<ProfileResponse?> = _userProfile

    private val _status = MutableStateFlow(false)
    val status: StateFlow<Boolean> = _status

    private val _listUsaha = MutableStateFlow<UsahaResponse?>(null)
    val listUsaha: StateFlow<UsahaResponse?> = _listUsaha

    private val _usahaDetail = MutableStateFlow<UsahaDetailResponse?>(null)
    val usahaDetail: StateFlow<UsahaDetailResponse?> = _usahaDetail

    private val _articleDetail = MutableStateFlow<ArticleDetail?>(null)
    val articleDetail: StateFlow<ArticleDetail?> = _articleDetail

    private val _llmResponse =
        MutableStateFlow(arrayListOf(Message("assistant", "")))
    val llmResponse: StateFlow<ArrayList<Message>> = _llmResponse

    private val _chatCounter = MutableStateFlow(0)
    val chatCounter: StateFlow<Int> = _chatCounter

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _stringResponse = MutableStateFlow("")
    val stringResponse : StateFlow<String> = _stringResponse

    init {
        clearStatus()
    }

    private fun getToken(): String = preferences.getString(TOKEN, "").toString()

    // User Profile
    fun getUser() {
        viewModelScope.launch {
            _userProfile.value = dataRepository.getUser(getToken())
        }
    }

    fun saveProfileChange(name: String, email: String) {
        viewModelScope.launch {
            _status.value = dataRepository.updateProfile(
                getToken(),
                ProfileRequest(name = name, email = email)
            )?.success ?: false
        }
    }

    // Usaha
    fun getListUsaha() {
        getToken()
        viewModelScope.launch {
            _listUsaha.value = dataRepository.getListUsaha(getToken())
        }
    }

    fun getUsahaDetail(id: Int) {
        viewModelScope.launch {
            _usahaDetail.value = dataRepository.getUsahaDetail(getToken(), id)
        }
    }

    fun createUsaha(usahaName: String, type: Int, tags: List<Tag>) {
        viewModelScope.launch {
            _status.value = dataRepository.createUsaha(
                getToken(),
                usahaRequest = UsahaRequest(usaha_name = usahaName, type = type, tags = tags)
            )?.success ?: false
        }
    }


    // Articles
    fun getAllArticles(): Flow<PagingData<ArticlesItem>> =
        dataRepository.getListArticle().cachedIn(viewModelScope)

    fun getUserArticles(): Flow<PagingData<ArticlesItem>> =
        dataRepository.getUserArticle(getToken()).cachedIn(viewModelScope)


    fun searchAllArticle(text: String): Flow<PagingData<ArticlesItem>> =
        dataRepository.searchAllArticle(text).cachedIn(viewModelScope)

    fun searchUserArticle(text: String): Flow<PagingData<ArticlesItem>> =
        dataRepository.searchUserArticle(getToken(), text).cachedIn(viewModelScope)


    fun getArticleDetail(id: Int) {
        viewModelScope.launch {
            _articleDetail.value = dataRepository.getArticleDetail(id)?.data
        }
    }

    fun createArticle(title: String, content: String, category: Int) {
        viewModelScope.launch {
            _status.value = dataRepository.createArticle(
                getToken(),
                CreateArticleRequest(
                    ArticleRequest(
                        title = title,
                        content = content,
                        image = "https://example.com/updated_image.jpg",
                        is_published = true
                    ),
                    category
                )
            )?.success ?: false
        }
    }

    fun clearStatus() {
        _status.value = false
    }

    private fun sendStreamChat(message: List<Message>) {
        _isLoading.value = true
        val llmRequest = LLMRequest(
            model = "Kawan-Usaha",
            stream = true,
            messages = message,
            max_tokens = 512,
            temperature = 0.5,
            top_p = 0.5
        )

        val jsonPayload = Gson().toJson(llmRequest)
        val mediaType = "application/json".toMediaType()
        val requestBody = jsonPayload.toRequestBody(mediaType)


        val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .build()

        val request = Request.Builder()
            .url("http://34.73.209.243:8000/v1/chat/completions")
            .header("Content-Type", "application/json")
            .addHeader("Accept", "text/event-stream")
            .post(requestBody)
            .build()

        EventSources.createFactory(client)
            .newEventSource(request = request, listener = eventSourceListener)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                client.newCall(request).enqueue(responseCallback = object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("DataRepository", "API Call Failure ${e.localizedMessage}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.e("DataRepository", "APi Call Success $response")
                    }
                })
            }
        }
    }

    private fun addCounter() {
        _chatCounter.value += 1
    }

    fun sendMsg(message: String) {
        _llmResponse.value.add(Message("user", message))
        addCounter()
        sendStreamChat(llmResponse.value)
    }

    fun getAllData(): LiveData<List<DbData>> = localRepository.getAllData()


    private val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            Log.e("SSE", "Connection Opened")
            _stringResponse.value = ""
            addCounter()
        }

        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
            Log.e("SSE", "Connection Closed")
            _llmResponse.value.add(Message("assistant", stringResponse.value))
            _isLoading.value = false
        }

        override fun onEvent(
            eventSource: EventSource,
            id: String?,
            type: String?,
            data: String
        ) {
            super.onEvent(eventSource, id, type, data)
            val gson = Gson().fromJson(data, LLMResponse::class.java)
            Log.e("SSE", "On Event Received! Data -: ${gson.choices[0].delta.content}")
            gson.choices[0].delta.content.toString().let {
                _stringResponse.value += if (it == "null") "" else it
            }
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Log.e("SSE", "On Failure -: ${response?.message}")
            addCounter()
            _stringResponse.value = "Error"
            _llmResponse.value.add(Message("assistant", stringResponse.value))
            _isLoading.value = false
        }
    }

    companion object {
        private const val TOKEN = "TOKEN"
    }
}

class MainViewModelFactory(
    private val preferences: SharedPreferences,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                Injection.provideRepository(),
                preferences,
                Injection.provideRepository(application)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

