package com.jetpack.kawanusaha.data

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String, val password_confirm: String)
data class VerificationRequest(val verification_code: String)
data class ForgotGenerateRequest(val email: String)
data class ForgotVerifyRequest(val password: String, val password_confirm: String, val verification_code: String)