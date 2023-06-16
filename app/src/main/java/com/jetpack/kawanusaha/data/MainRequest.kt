package com.jetpack.kawanusaha.data

data class ProfileRequest(
    val name: String,
    val email: String,
)

data class UsahaRequest(
    val usahaname: String,
    val type: Int,
    val tags: List<Tag>,
)

data class IdRequest (
    val id: Int
)

data class Tag (
    val name: String
)

data class CreateArticleRequest(
    val article : ArticleRequest,
    val category: Int
)

data class ArticleRequest (
    val title: String,
    val content: String,
    val is_published: Boolean
)

data class LLMRequest(
    val stream: Boolean,
    val messages: List<Message>,
    val model: String,
    val max_tokens: Int,
    val temperature: Double,
    val top_p : Double
)

data class LLMContinueChat (
    val stream: Boolean,
    val prompt: String,
    val model: String,
    val max_tokens: Int,
    val temperature: Double,
    val top_p : Double
)
