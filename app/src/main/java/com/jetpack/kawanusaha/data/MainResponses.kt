package com.jetpack.kawanusaha.data

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

