package com.jetpack.kawanusaha.data

data class LoginResponse(
    val data: Data? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class RegisterResponse(
    val data: Data? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class GenerateVerificationResponse(
    val data: Any? = null,
    val success: Boolean,
    val message: String,
)

data class Data(
	val name: String? = null,
	val email: String? = null,
	val token: String? = null
)

