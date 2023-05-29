//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)

# ApiService

[androidJvm]\
interface [ApiService](index.md)

## Functions

| Name | Summary |
|---|---|
| [forgotGenerate](forgot-generate.md) | [androidJvm]<br>@POST(value = &quot;auth/forgot-password/generate&quot;)<br>abstract fun [forgotGenerate](forgot-generate.md)(@BodyforgotGenerateRequest: [ForgotGenerateRequest](../../com.jetpack.kawanusaha.data/-forgot-generate-request/index.md)): Call&lt;[GenerateVerificationResponse](../../com.jetpack.kawanusaha.data/-generate-verification-response/index.md)&gt; |
| [forgotVerify](forgot-verify.md) | [androidJvm]<br>@POST(value = &quot;auth/forgot-password/verify&quot;)<br>abstract fun [forgotVerify](forgot-verify.md)(@BodyforgotVerifyRequest: [ForgotVerifyRequest](../../com.jetpack.kawanusaha.data/-forgot-verify-request/index.md)): Call&lt;[GenerateVerificationResponse](../../com.jetpack.kawanusaha.data/-generate-verification-response/index.md)&gt; |
| [generate](generate.md) | [androidJvm]<br>@POST(value = &quot;auth/generate&quot;)<br>abstract fun [generate](generate.md)(): Call&lt;[GenerateVerificationResponse](../../com.jetpack.kawanusaha.data/-generate-verification-response/index.md)&gt; |
| [login](login.md) | [androidJvm]<br>@POST(value = &quot;auth/login&quot;)<br>abstract fun [login](login.md)(@BodyloginRequest: [LoginRequest](../../com.jetpack.kawanusaha.data/-login-request/index.md)): Call&lt;[LoginResponse](../../com.jetpack.kawanusaha.data/-login-response/index.md)&gt; |
| [register](register.md) | [androidJvm]<br>@POST(value = &quot;auth/register&quot;)<br>abstract fun [register](register.md)(@BodyregisterRequest: [RegisterRequest](../../com.jetpack.kawanusaha.data/-register-request/index.md)): Call&lt;[RegisterResponse](../../com.jetpack.kawanusaha.data/-register-response/index.md)&gt; |
| [verify](verify.md) | [androidJvm]<br>@POST(value = &quot;auth/verify&quot;)<br>abstract fun [verify](verify.md)(@BodyverificationRequest: [VerificationRequest](../../com.jetpack.kawanusaha.data/-verification-request/index.md)): Call&lt;[VerificationResponse](../../com.jetpack.kawanusaha.data/-verification-response/index.md)&gt; |
