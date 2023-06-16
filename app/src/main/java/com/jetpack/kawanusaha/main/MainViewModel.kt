package com.jetpack.kawanusaha.main

import android.app.Application
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.jetpack.kawanusaha.data.*
import com.jetpack.kawanusaha.`in`.Injection
import com.jetpack.kawanusaha.tools.getFileFromUri
import com.jetpack.kawanusaha.tools.reduceFileImage
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
import java.time.LocalDate
import java.util.concurrent.TimeUnit

/**
 * ViewModel class for everything in the system.
 * @property dataRepository The instance of DataRepository used for data operations.
 * @property preferences The instance of SharedPreferences used for storing preferences.
 * @property application The instance of Application representing the current application context.
 */
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _stringResponse = MutableStateFlow("")
    val stringResponse: StateFlow<String> = _stringResponse

    private val _articleCache = MutableStateFlow<CreateArticleRequest?>(null)
    val articleCache: StateFlow<CreateArticleRequest?> = _articleCache

    private val _selectedCategory = MutableStateFlow(0)
    val selectedCategory: StateFlow<Int> = _selectedCategory

    private val _favouriteList = MutableStateFlow<List<ArticlesItem>?>(null)
    val favouriteList: StateFlow<List<ArticlesItem>?> = _favouriteList

    private val _tagList = MutableStateFlow<List<TagItem>?>(null)
    val tagList: StateFlow<List<TagItem>?> = _tagList

    private val _stopReason = MutableStateFlow<String?>("")
    val stopReason: StateFlow<String?> = _stopReason

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

    init {
        getTag()
        getCategory()
        clearStatus()
        clearImage()
    }

    // DEFAULT GETTER & SETTER
    /**
     * Sets the image file with the provided URI.
     * @param uri The URI of the image file to set.
     */
    fun setImage(uri: Uri) {
        _imageFile.value = uri
    }

    /**
     * Clears the image file value by setting it to a null Uri.
     */
    fun clearImage() {
        _imageFile.value = Uri.parse("file://dev/null")
    }

    /**
     * Clears the status value by setting it to false.
     */
    fun clearStatus() {
        _status.value = false
    }

    /**
     * Clears the LLM response cache by resetting it to the default initial message.
     */
    fun clearCache() {
        _llmResponse.value = arrayListOf(
            Message(
                "assistant",
                "Saya adalah AI asisten pembantu UMKM. Tugas Saya adalah menjadi mentor virtual untuk UMKM. Silahkan bertanya apapun dan saya akan menjawabnya."
            )
        )
    }

    /**
     * Clears the article cache by setting it to null.
     */
    fun clearArticleCache() {
        _articleCache.value = null
    }

    /**
     * Retrieves the token from the SharedPreferences.
     * @return The token value as a String, or an empty string if the token is not found.
     */
    private fun getToken(): String = preferences.getString(TOKEN, "").toString()

    /**
     * Retrieves the tag list from the data repository and updates the `_tagList` FlowState
     */
    private fun getTag() {
        viewModelScope.launch {
            _tagList.value = dataRepository.getTag()?.data?.tag
        }
    }


    // CATEGORY
    /**
     * Retrieves the category list from the data repository and updates the `_categoryList` FlowState.
     */
    private fun getCategory() {
        viewModelScope.launch {
            _categoryList.value = dataRepository.getCategory()
        }
    }

    /**
     * Sets the selected category.
     * @param id The ID of the selected category.
     */
    fun selectThisCategory(id: Int) {
        _selectedCategory.value = id
    }


    // PROFILE
    /**
     * Retrieves the user profile from the data repository and updates the `_userProfile` FlowState.
     */
    fun getUser() {
        viewModelScope.launch {
            _userProfile.value = dataRepository.getUser(getToken())
        }
    }

    /**
     * Saves the profile changes by updating the user's name and email.
     *
     * @param name The new name for the user's profile.
     * @param email The new email for the user's profile.
     */
    fun saveProfileChange(name: String, email: String) {
        viewModelScope.launch {
            if (imageFile.value != Uri.parse("file://dev/null")) {
                val file = getFileFromUri(application.applicationContext, imageFile.value) as File
                val compressedFile = reduceFileImage(file)
                val requestImageFile =
                    compressedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    name = "image",
                    filename = file.name,
                    body = requestImageFile
                )
                _status.value = dataRepository.updateProfile(
                    getToken(),
                    imageMultipart,
                    imageChanged = "true",
                    ProfileRequest(name = name, email = email)
                )?.success ?: false
            } else {
                _status.value = dataRepository.updateProfile(
                    getToken(), null,
                    imageChanged = "false",
                    ProfileRequest(name = name, email = email)
                )?.success ?: false
            }
        }
    }


    // USAHA
    /**
     * Retrieves the list of Usaha.
     */
    fun getListUsaha() {
        getToken()
        viewModelScope.launch {
            _listUsaha.value = dataRepository.getListUsaha(getToken())
        }
    }

    /**
     * Retrieves the details of a specific Usaha.
     * @param id The ID of the Usaha.
     */
    fun getUsahaDetail(id: Int) {
        viewModelScope.launch {
            _usahaDetail.value = dataRepository.getUsahaDetail(getToken(), id)
        }
    }

    /**
     * Creates a new Usaha.
     * @param usahaName The name of the Usaha.
     * @param type The type of the Usaha.
     * @param tags The list of tags associated with the Usaha.
     */
    fun createUsaha(usahaName: String, type: Int, tags: List<Tag>) {
        viewModelScope.launch {
            _status.value = dataRepository.createUsaha(
                getToken(),
                usahaRequest = UsahaRequest(usahaname = usahaName, type = type, tags = tags)
            )?.success ?: false
        }
    }

    /**
     * Delete a specific Usaha.
     * @param id The ID of the Usaha.
     */
    fun deleteUsaha(id: Int) {
        viewModelScope.launch {
            _status.value = dataRepository.deleteUsaha(
                getToken(),
                id
            )?.success ?: false
        }
    }


    // Articles
    /**
     * Retrieves the user articles using a Flow of PagingData.
     * @return A Flow of PagingData containing the user articles.
     */
    fun getUserArticles(): Flow<PagingData<ArticlesItem>> =
        dataRepository.getUserArticle(getToken()).cachedIn(viewModelScope)

    /**
     * Filters articles based on the given text and category.
     * If the category is 0, it searches all articles using the text.
     * Otherwise, it filters articles based on the selected category.
     * @param text The text to search for.
     * @param category The category ID to filter by.
     * @return A Flow of PagingData containing the filtered articles.
     */
    fun filterAllArticle(text: String, category: Int): Flow<PagingData<ArticlesItem>> =
        if (category == 0) {
            dataRepository.searchAllArticle(text).cachedIn(viewModelScope)
        } else {
            filterCategory(selectedCategory.value)
        }

    /**
     * Filters articles based on the selected category.
     * @param id The ID of the selected category.
     * @return A Flow of PagingData containing the filtered articles.
     */
    private fun filterCategory(id: Int): Flow<PagingData<ArticlesItem>> =
        dataRepository.getCategorizedArticles(id).cachedIn(viewModelScope)

    /**
     * Retrieves the article detail for the specified ID.
     * @param id The ID of the article.
     */
    fun getArticleDetail(id: Int) {
        viewModelScope.launch {
            _articleDetail.value = dataRepository.getArticleDetail(id)?.data
        }
    }

    /**
     * Saves the article cache with the provided title, description, and category.
     * @param title The title of the article.
     * @param description The description or content of the article.
     * @param category The category of the article.
     */
    fun saveArticleCache(title: String, description: String, category: Int) {
        _articleCache.value = CreateArticleRequest(
            ArticleRequest(
                title = title,
                content = description,
                is_published = true
            ),
            category
        )
    }

    /**
     * Creates a new article with the provided title, content, and category.
     * If an image file is specified, it is included in the multipart request.
     * If no image file is specified, the article is created without an image.
     */
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

    private fun createNewArticle(
        imageMultipart: MultipartBody.Part?,
        createArticleRequest: CreateArticleRequest
    ) {
        viewModelScope.launch {
            _status.value = dataRepository.createArticle(
                getToken(),
                imageMultipart,
                createArticleRequest
            )?.success ?: false
        }
    }

    /**
     * Remove the article of the specified ID.
     * @param id The ID of the article.
     */
    fun deleteArticle(id: Int) {
        viewModelScope.launch {
            dataRepository.deleteArticle(getToken(), id)
        }
    }


    // FAVORITE
    /**
     * Sets the specified article as favorite.
     * @param id The ID of the article to set as favorite.
     */
    fun setFavourite(id: Int) {
        viewModelScope.launch {
            dataRepository.setFavourite(getToken(), id)
        }
    }

    /**
     * Retrieves the list of favorite articles.
     */
    fun getFavourite() {
        viewModelScope.launch {
            _favouriteList.value = dataRepository.getFavourite(getToken())?.data?.articles
        }
    }

    /**
     * Delete the specified article from favorite.
     * @param id The ID of the article to remove from favorite.
     */
    fun deleteFavourite(id: Int) {
        viewModelScope.launch {
            dataRepository.deleteFavourite(getToken(), id)
        }
    }


    // CHAT BOT TEXT STREAM
    /**
     * Sends a message from the user to the chat stream.
     *
     * @param message The message to send.
     */
    suspend fun sendMsg(message: String) {
        if (llmResponse.value.size % GENERATE_COUNTER == 0 || llmResponse.value.size % GENERATE_COUNTER == 1) {
            viewModelScope.launch {
                val llmRequest = LLMRequest(
                    model = "Kawan-Usaha",
                    stream = false,
                    messages = if (llmResponse.value.size > 1){
                                listOf(llmResponse.value[llmResponse.value.lastIndex-1], llmResponse.value.last())
                            } else {
                                   llmResponse.value
                            },
                    max_tokens = 512,
                    temperature = 0.5,
                    top_p = 0.5
                )
                dataRepository.generateArticle(getToken(), llmRequest)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dummyResponse: ArrayList<Message> = arrayListOf()
            dummyResponse.add(Message(llmResponse.value.last().role, llmResponse.value.last().content.slice(0..200)))
            _llmResponse.value.add(Message("user", message))
            dummyResponse.add(Message("user", enhancePrompt(message)))

            Log.e("LLM RESPONSE", llmResponse.value.toString())
            Log.e("DUMMY RESPONSE", dummyResponse.toString())
            sendStreamChat(dummyResponse)
        } else {
            _llmResponse.value.add(Message("user", message))
            sendStreamChat(_llmResponse.value)
        }
    }


    /**
     * Enhances the prompt by adding information from the internet.
     *
     * @param message The message or instruction to be enhanced.
     * @return The enhanced prompt with information from the internet.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun enhancePrompt(message: String): String {
        return withContext(Dispatchers.IO) {
            var enhancedPrompt: String = "Saya akan memberi Anda pertanyaan atau instruksi. " +
                    "Tujuan Anda sebagai seorang ahli Usaha Mikro Kecil Menengah adalah untuk menjawab pertanyaan saya atau memenuhi instruksi saya.\n" +
                    "Pertanyaan atau instruksi saya adalah: $message. \n" +
                    "Untuk referensi Anda, tanggal hari ini adalah ${LocalDate.now()}.\n" +
                    "Pertanyaan atau instruksi, atau hanya sebagian saja, mungkin memerlukan informasi yang relevan dari internet untuk memberikan " +
                    "jawaban yang memuaskan. Oleh karena itu, di bawah ini adalah informasi yang diperlukan yang diperoleh dari internet, yang menentukan " +
                    "konteks untuk menjawab pertanyaan atau memenuhi instruksi. Anda akan menulis balasan komprehensif untuk pertanyaan atau instruksi yang " +
                    "diberikan. Pastikan untuk mengutip hasil menggunakan notasi [[NOMOR](URL)] setelah referensi."
            val text = dataRepository.searchInternet(message)
            var iterator = 0
            text?.web?.results?.forEach { content ->
                if (content.extraSnippets != null && iterator < WEB_COUNT) {
                    if (content.extraSnippets[0] != "null") {
                        iterator++
                        enhancedPrompt +=
                            "NOMOR $iterator \n" +
                                    "URL : ${content.url}\n" +
                                    "JUDUL : ${content.title}\n" +
                                    "CONTENT : ${
                                        if (content.extraSnippets[0].length > 200) content.extraSnippets[0].slice(
                                            0..200
                                        ) else content.extraSnippets[0]
                                    }\n"
                    }
                }
            }
            enhancedPrompt
        }
    }


    /**
     * Sends the chat messages to the stream and handles the response.
     *
     * @param message The list of messages to send.
     */
    private fun sendStreamChat(message: List<Message>) {
        _isLoading.value = true
        val llmRequest = LLMRequest(
            model = "Kawan-Usaha",
            stream = true,
            messages = if (message.lastIndex > 1){
                        listOf(message[message.lastIndex - 1], message.last())
                    } else {
                           message
                   },
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
            .url("${API_HOST}v1/chat/completions")
            .header("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + getToken())
            .addHeader("Accept", "text/event-stream")
            .post(requestBody)
            .build()

        EventSources.createFactory(client)
            .newEventSource(request = request, listener = eventSourceListener)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                client.newCall(request).enqueue(responseCallback = object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("MainViewModel", "API Call Failure ${e.localizedMessage}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.e("MainViewModel", "APi Call Success $response")
                    }
                })
            }
        }
    }

    fun continueGenerate() {
        val message = llmResponse.value.last().content
        Log.e("tag", message.toString())
        _isLoading.value = true
        val llmRequest = LLMContinueChat(
            model = "Kawan-Usaha",
            stream = true,
            prompt = message,
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
            .url("${API_HOST}v1/completions")
            .header("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + getToken())
            .addHeader("Accept", "text/event-stream")
            .post(requestBody)
            .build()

        EventSources.createFactory(client)
            .newEventSource(request = request, listener = eventSourceListener)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                client.newCall(request).enqueue(responseCallback = object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("ContinueGenerate", "API Call Failure ${e.localizedMessage}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.e("ContinueGenerate", "APi Call Success $response")
                    }
                })
            }
        }
    }

    /**
     * EventSourceListener implementation used for handling events from EventSource.
     */
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
            Log.e("DATA", gson.toString())
            gson.choices[0].delta.content.toString().let {
                _stringResponse.value += if (it == "null") "" else it
            }
            _stopReason.value = gson.choices[0].finish_reason.toString()
            if (stopReason.value == "stop" || stopReason.value == "length") eventSource.cancel()
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            if (response?.code != 200) {
                _stringResponse.value = "Error"
                Log.e("Streaming Data", response.toString())
            }
            _llmResponse.value.add(Message("assistant", stringResponse.value))
            _isLoading.value = false
            _stringResponse.value = ""
        }
    }

    companion object {
        private const val TOKEN = "TOKEN"
        private const val API_HOST = "https://api.kawan-usaha.com/"
        private const val GENERATE_COUNTER = 7
        private const val WEB_COUNT = 3
    }
}

/**
 * Factory class for creating instances of MainViewModel.
 *
 * @param preferences The SharedPreferences instance for accessing preferences.
 * @param application The Application instance.
 */
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

