//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[getUserArticles](get-user-articles.md)

# getUserArticles

[androidJvm]\

@GET(value = &quot;article/owned&quot;)

abstract suspend fun [getUserArticles](get-user-articles.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Query(value = &quot;page&quot;)page: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;page_size&quot;)page_size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ArticleResponse](../../com.jetpack.kawanusaha.data/-article-response/index.md)
