package com.jetpack.kawanusaha.data

data class ProfileRequest(
    val name: String,
    val email: String,
)

data class UsahaRequest(
    val usaha_name: String,
    val type: Int,
    val tags: List<Tag>,
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
    val image: String,
    val is_published: Boolean
)

data class LLMRequest(
    val stream: Boolean,
    val messages: List<Message>,
    val model: String
)
