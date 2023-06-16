//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[updateProfile](update-profile.md)

# updateProfile

[androidJvm]\

@Multipart

@PATCH(value = &quot;user/profile&quot;)

abstract fun [updateProfile](update-profile.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Partimage: MultipartBody.Part?, @Part(value = &quot;user&quot;)profileRequest: [ProfileRequest](../../com.jetpack.kawanusaha.data/-profile-request/index.md)): Call&lt;[DefaultResponse](../../com.jetpack.kawanusaha.data/-default-response/index.md)&gt;
