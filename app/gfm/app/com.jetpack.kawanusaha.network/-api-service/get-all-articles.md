//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[getAllArticles](get-all-articles.md)

# getAllArticles

[androidJvm]\

@GET(value = &quot;article&quot;)

abstract suspend fun [getAllArticles](get-all-articles.md)(@Query(value = &quot;page&quot;)page: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;page_size&quot;)page_size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ArticleResponse](../../com.jetpack.kawanusaha.data/-article-response/index.md)
