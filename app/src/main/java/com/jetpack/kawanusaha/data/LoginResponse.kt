package com.jetpack.kawanusaha.data

data class LoginResponse(
    val data: Data? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class Data(
	val name: String? = null,
	val email: String? = null,
	val token: String? = null
)

