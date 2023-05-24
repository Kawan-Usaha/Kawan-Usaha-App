package com.jetpack.kawanusaha.utility

import com.jetpack.kawanusaha.data.Data
import com.jetpack.kawanusaha.data.GenerateVerificationResponse
import com.jetpack.kawanusaha.data.LoginResponse
import com.jetpack.kawanusaha.data.RegisterResponse

object DataDummy {
    fun generateLoginDummy(): LoginResponse{
        return LoginResponse(
            data = Data(
                name = "Name Dummy",
                email = "email@dummy.com",
                token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODU3MDIxODUsImlhdCI6MTY4NDQ5MjU4NSwibmJmIjoxNjg0NDkyNTg1LCJzdWIiOiI0NzRjM2Y5Zi0wM2MyLTQ3NzMtYjZjMS0yZmM3NTQ2YWE0YTEifQ.V1FMqaloryrrA_WGhqnjxgTKj62LVGHPIi9kNyonvtrgVrXa3dSjz0koXnR5S6e419FWznnN5qUbf4m4VvpdBw"),
            success = true,
            message = "Login success")
    }
    fun generateRegisterDummy(): RegisterResponse{
        return RegisterResponse(
            data = Data(
                name = "Name Dummy",
                email = "email@dummy.com",
                token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODU3MDIxODUsImlhdCI6MTY4NDQ5MjU4NSwibmJmIjoxNjg0NDkyNTg1LCJzdWIiOiI0NzRjM2Y5Zi0wM2MyLTQ3NzMtYjZjMS0yZmM3NTQ2YWE0YTEifQ.V1FMqaloryrrA_WGhqnjxgTKj62LVGHPIi9kNyonvtrgVrXa3dSjz0koXnR5S6e419FWznnN5qUbf4m4VvpdBw"),
            success = true,
            message = "Login success")
    }

    fun generateGenerateDummy(): GenerateVerificationResponse{
        return GenerateVerificationResponse(
            data = null,
            success = true,
            message = "Email verification sent"
        )
    }
}

