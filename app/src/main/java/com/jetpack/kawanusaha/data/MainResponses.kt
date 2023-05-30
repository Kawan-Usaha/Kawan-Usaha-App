package com.jetpack.kawanusaha.data

data class DefaultResponse(
	val success: Boolean,
	val message: String
)

data class ProfileResponse(
	val data: ProfileData,
	val success: Boolean,
	val message: String
)

data class ProfileData(
	val createdAt: String,
	val name: String,
	val verified: Boolean,
	val usaha: Any?,
	val articles: Any?,
	val userId: String,
	val email: String,
	val updatedAt: String
)

data class UsahaResponse(
	val data: List<UsahaData?>,
	val success: Boolean,
	val message: String
)

data class UsahaDetailResponse(
	val data: UsahaData?,
	val success: Boolean,
	val message: String
)

data class UsahaData (
	val id: Int,
	val tags: List<Tag>,
	val type: Int,
	val usaha_name : String,
	val createdAt: String?,
	val updatedAt: String?
)

data class CreateUsahaData(
	val usahaName: String,
	val updatedAt: String,
	val userId: String,
	val createdAt: String,
	val id: Int,
	val type: Int,
	val tags: List<UsahaTagsItem>
)

data class UsahaTagsItem(
	val updatedAt: String,
	val name: String,
	val createdAt: String,
	val usaha: Any,
	val id: Int,
	val category: Any
)

data class ArticleResponse (
	val data: ArticleData,
	val success: Boolean,
	val message: String
)

data class ArticleData(
	val page: Int,
	val articles: List<ArticlesItem>,
	val pageSize: Int,
	val totalArticles: Int
)

data class ArticlesItem(
	val updatedAt: String,
	val isPublished: Boolean,
	val createdAt: String,
	val id: Int,
	val category: List<Category>,
	val title: String
)

data class ArticleDetailResponse(
	val data: ArticleDetail,
	val success: Boolean,
	val message: String
)

data class ArticleDetail(
	val updatedAt: String,
	val isPublished: Boolean,
	val createdAt: String,
	val id: Int,
	val category: List<Category>,
	val title: String,
	val content: String,
	val user: String
)


data class Category(
	val id: Int,
	val title: String,
	val image: String,
	val tags: Any,
	val articles: Any,
	val createdAt: String,
	val updatedAt: String
)

