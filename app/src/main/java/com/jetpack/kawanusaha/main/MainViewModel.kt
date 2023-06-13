package com.jetpack.kawanusaha.main

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.`in`.Injection
import com.jetpack.kawanusaha.tools.getFileFromUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val dataRepository: DataRepository,
    private val preferences: SharedPreferences,
    private val application: Application
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

    private val _imageFile = MutableStateFlow<Uri>(Uri.parse("file://dev/null"))
    val imageFile: StateFlow<Uri> = _imageFile

    private val _categoryList = MutableStateFlow<CategoryResponse?>(null)
    val categoryList: StateFlow<CategoryResponse?> = _categoryList

    private val _llmResponse =
        MutableStateFlow(
            arrayListOf(
                Message(
                    "assistant",
                    "Saya adalah AI asisten pembantu UMKM berbahasa Indonesia. Tugas Saya adalah menjadi mentor virtual untuk UMKM. Silahkan bertanya apapun dan saya akan menjawabnya."
                )
            )
        )

    val llmResponse: StateFlow<ArrayList<Message>> = _llmResponse


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _stringResponse = MutableStateFlow("")
    val stringResponse: StateFlow<String> = _stringResponse

    private val _articleCache = MutableStateFlow<CreateArticleRequest?>(null)
    val articleCache: StateFlow<CreateArticleRequest?> = _articleCache

    private val _selectedCategory = MutableStateFlow(0)
    val selectedCategory: StateFlow<Int> = _selectedCategory


    init {
        clearStatus()
    }

    private fun getToken(): String = preferences.getString(TOKEN, "").toString()

    // Category
    fun getCategory() {
        viewModelScope.launch {
            _categoryList.value = dataRepository.getCategory()
        }
    }

    fun selectThisCategory(id: Int) {
        _selectedCategory.value = id
    }


    // User Profile
    fun getUser() {
        viewModelScope.launch {
            _userProfile.value = dataRepository.getUser(getToken())
        }
    }
    fun saveProfileChange(name: String, email: String) {
        viewModelScope.launch {
            if (imageFile.value != Uri.parse("file://dev/null")) {
                val file = getFileFromUri(application.applicationContext, imageFile.value) as File
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    name = "image",
                    filename = file.name,
                    body = requestImageFile
                )
                _status.value = dataRepository.updateProfile(
                    getToken(),
                    imageMultipart,
                    ProfileRequest(name = name, email = email)
                )?.success ?: false
            } else {
                _status.value = dataRepository.updateProfile(
                    getToken(), null,
                    ProfileRequest(name = name, email = email)
                )?.success ?: false
            }
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
        if (selectedCategory.value == 0) {
            dataRepository.searchAllArticle(text).cachedIn(viewModelScope)
        } else {
            filterCategory(selectedCategory.value)
        }

    private fun filterCategory(id: Int): Flow<PagingData<ArticlesItem>> =
        dataRepository.getCategorizedArticles(id).cachedIn(viewModelScope)

    fun getArticleDetail(id: Int) {
        viewModelScope.launch {
            _articleDetail.value = dataRepository.getArticleDetail(id)?.data
        }
    }

    fun saveArticleCache(title: String, description: String, category: Int) {
        _articleCache.value = CreateArticleRequest(
            ArticleRequest(
                title = title,
                content = description,
                is_published = false
            ),
            category
        )
    }

    fun clearArticleCache() {
        _articleCache.value = null
    }

    fun setImage(uri: Uri) {
        _imageFile.value = uri
    }

    private fun createNewArticle (imageMultipart: MultipartBody.Part?,createArticleRequest: CreateArticleRequest){
        viewModelScope.launch {
            _status.value = dataRepository.createArticle(
                getToken(),
                imageMultipart,
                createArticleRequest
            )?.success ?: false
        }
    }

    fun createArticle(title: String, content: String, category: Int) {
        viewModelScope.launch {
            val articleRequest = CreateArticleRequest(
                ArticleRequest(
                    title = title,
                    content = content,
                    is_published = true
                ),
                category
            )
            if (imageFile.value != Uri.parse("file://dev/null")) {
                val file = getFileFromUri(application.applicationContext, imageFile.value) as File
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    name = "image",
                    filename = file.name,
                    body = requestImageFile
                )
                createNewArticle(imageMultipart, articleRequest)
            } else {
                createNewArticle(null, articleRequest)
            }
        }
    }

    fun clearImage() {
        _imageFile.value = Uri.parse("file://dev/null")
    }

    fun clearStatus() {
        _status.value = false
    }

    fun clearCache() {
        _llmResponse.value = arrayListOf(
            Message(
                "assistant",
                "Saya adalah AI asisten pembantu UMKM. Tugas Saya adalah menjadi mentor virtual untuk UMKM. Silahkan bertanya apapun dan saya akan menjawabnya."
            )
        )
    }

    private fun sendStreamChat(message: List<Message>) {
        _isLoading.value = true
        val llmRequest = LLMRequest(
            model = "Kawan-Usaha",
            stream = true,
            messages = message,
            max_tokens = 512,
            temperature = 0.5,
            top_p = 1.0
        )

        val jsonPayload = Gson().toJson(llmRequest)
        val mediaType = "application/json".toMediaType()
        val requestBody = jsonPayload.toRequestBody(mediaType)

        val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .build()

        val request = Request.Builder()
            .url("http://$API_HOST/chat/completions")
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

    fun sendMsg(message: String) {
        _llmResponse.value.add(Message("user", message))
        if (llmResponse.value.size % GENERATE_COUNTER == 0) {
            viewModelScope.launch {
                generateAIArticle()
            }
        }
        sendStreamChat(llmResponse.value)
    }


    private var topic: String = ""
    private var articleText: String = ""
    private suspend fun generateAIArticle() {
        val message = llmResponse.value
        message.add(Message("user", "Apa topik dari percakapan diatas?"))
        topic = dataRepository.chatResult(
            llmRequest = LLMRequest(
                model = "Kawan-Usaha",
                stream = false,
                messages = message,
                max_tokens = 512,
                temperature = 0.5,
                top_p = 1.0
            )
        )?.choices?.get(0)?.message?.content ?: "No Topic"
        if (topic == "No Topic") {
            Log.e("Generate Article", "Cannot get topic")
        } else {
            message.add(
                Message(
                    "user",
                    "Buatkan artikel untuk mengedukasi saya mengenai topik tersebut. Buat dengan selengkap lengkapnya"
                )
            )
            generateAIArticle2(message)
        }
    }

    private suspend fun generateAIArticle2(message: ArrayList<Message>) {
        val response = dataRepository.chatResult(
            llmRequest = LLMRequest(
                model = "Kawan-Usaha",
                stream = false,
                messages = message,
                max_tokens = 512,
                temperature = 0.5,
                top_p = 1.0
            )
        )
        articleText += response?.choices?.get(0)?.message?.content ?: ""
        if (response != null && response.choices[0].finish_reason != "stop") {
            message.add(Message("user", "Lanjutkan dari pesan terakhir anda"))
            generateAIArticle2(message)
        }
        if (response != null && response.choices[0].finish_reason == "stop"){
             createNewArticle(
                 imageMultipart = null,
                 createArticleRequest = CreateArticleRequest(
                    article = ArticleRequest(
                        title = topic,
                        content = articleText,
                        is_published = true
                    ), category = 1
                )
             )
        }
    }

    private val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            _stringResponse.value = ""
        }

        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
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
            gson.choices[0].delta.content.toString().let {
                _stringResponse.value += if (it == "null") "" else it
            }
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            if (response?.message != "OK") {
                _stringResponse.value = "Error"
                Log.e("TAG", response?.body?.string().toString())
            }
            _llmResponse.value.add(Message("assistant", stringResponse.value))
            _isLoading.value = false
            _stringResponse.value = ""
        }
    }

    companion object {
        private const val TOKEN = "TOKEN"
        private const val GENERATE_COUNTER = 6
        private const val API_HOST = "34.170.183.54:5000"
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
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

