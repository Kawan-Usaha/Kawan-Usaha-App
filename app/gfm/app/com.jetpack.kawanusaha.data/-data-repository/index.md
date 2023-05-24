//[app](../../../index.md)/[com.jetpack.kawanusaha.data](../index.md)/[DataRepository](index.md)

# DataRepository

[androidJvm]\
class [DataRepository](index.md)(apiService: [ApiService](../../com.jetpack.kawanusaha.network/-api-service/index.md))

## Constructors

| | |
|---|---|
| [DataRepository](-data-repository.md) | [androidJvm]<br>constructor(apiService: [ApiService](../../com.jetpack.kawanusaha.network/-api-service/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [forgotGenerate](forgot-generate.md) | [androidJvm]<br>suspend fun [forgotGenerate](forgot-generate.md)(forgotGenerateRequest: [ForgotGenerateRequest](../-forgot-generate-request/index.md)): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
| [forgotVerify](forgot-verify.md) | [androidJvm]<br>suspend fun [forgotVerify](forgot-verify.md)(forgotVerifyRequest: [ForgotVerifyRequest](../-forgot-verify-request/index.md)): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
| [generate](generate.md) | [androidJvm]<br>suspend fun [generate](generate.md)(): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
| [login](login.md) | [androidJvm]<br>suspend fun [login](login.md)(loginRequest: [LoginRequest](../-login-request/index.md)): [LoginResponse](../-login-response/index.md)? |
| [register](register.md) | [androidJvm]<br>suspend fun [register](register.md)(registerRequest: [RegisterRequest](../-register-request/index.md)): [RegisterResponse](../-register-response/index.md)? |
| [verify](verify.md) | [androidJvm]<br>suspend fun [verify](verify.md)(verificationRequest: [VerificationRequest](../-verification-request/index.md)): [VerificationResponse](../-verification-response/index.md)? |
