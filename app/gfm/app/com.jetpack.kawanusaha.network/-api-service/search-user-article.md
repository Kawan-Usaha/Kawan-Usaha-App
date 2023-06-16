//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[searchUserArticle](search-user-article.md)

# searchUserArticle

[androidJvm]\

@GET(value = &quot;article/owned/search&quot;)

abstract suspend fun [searchUserArticle](search-user-article.md)(@Header(value = &quot;Authorization&quot;)token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Query(value = &quot;page&quot;)page: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;page_size&quot;)page_size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;title&quot;)title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ArticleResponse](../../com.jetpack.kawanusaha.data/-article-response/index.md)
