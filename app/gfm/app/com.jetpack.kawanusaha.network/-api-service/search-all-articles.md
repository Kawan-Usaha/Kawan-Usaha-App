//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[searchAllArticles](search-all-articles.md)

# searchAllArticles

[androidJvm]\

@GET(value = &quot;article/search&quot;)

abstract suspend fun [searchAllArticles](search-all-articles.md)(@Query(value = &quot;page&quot;)page: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;page_size&quot;)page_size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;title&quot;)title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ArticleResponse](../../com.jetpack.kawanusaha.data/-article-response/index.md)
