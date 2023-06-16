//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[chatResponse](chat-response.md)

# chatResponse

[androidJvm]\

@POST(value = &quot;v1/chat/completions&quot;)

abstract fun [chatResponse](chat-response.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Bodyrequest: [LLMRequest](../../com.jetpack.kawanusaha.data/-l-l-m-request/index.md)): Call&lt;[LLMResponse](../../com.jetpack.kawanusaha.data/-l-l-m-response/index.md)&gt;
