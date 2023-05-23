package com.jetpack.kawanusaha.data

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String, val password_confirm: String)

data class VerificationRequest(val verification_code: String)