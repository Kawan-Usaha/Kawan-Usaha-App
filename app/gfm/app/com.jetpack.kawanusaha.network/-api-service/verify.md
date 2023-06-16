//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[verify](verify.md)

# verify

[androidJvm]\

@POST(value = &quot;auth/verify&quot;)

abstract fun [verify](verify.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @BodyverificationRequest: [VerificationRequest](../../com.jetpack.kawanusaha.data/-verification-request/index.md)): Call&lt;[GenerateVerificationResponse](../../com.jetpack.kawanusaha.data/-generate-verification-response/index.md)&gt;
