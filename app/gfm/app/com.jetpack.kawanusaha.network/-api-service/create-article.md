//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[createArticle](create-article.md)

# createArticle

[androidJvm]\

@Multipart

@POST(value = &quot;article/create&quot;)

abstract fun [createArticle](create-article.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Partimage: MultipartBody.Part?, @Part(value = &quot;article&quot;)createArticleRequest: [CreateArticleRequest](../../com.jetpack.kawanusaha.data/-create-article-request/index.md)): Call&lt;[DefaultResponse](../../com.jetpack.kawanusaha.data/-default-response/index.md)&gt;
