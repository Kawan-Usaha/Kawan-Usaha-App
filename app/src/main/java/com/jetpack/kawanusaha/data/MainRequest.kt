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

data class Tag (
    val name: String
)

data class ArticleRequest (
    val title: String,
    val content: String,
    val image: String,
    val is_published: Boolean
)