//[app](../../../index.md)/[com.jetpack.kawanusaha.network](../index.md)/[ApiService](index.md)/[getCategorizedArticle](get-categorized-article.md)

# getCategorizedArticle

[androidJvm]\

@GET(value = &quot;article/category&quot;)

abstract suspend fun [getCategorizedArticle](get-categorized-article.md)(@Query(value = &quot;page&quot;)page: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;page_size&quot;)page_size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @Query(value = &quot;category&quot;)category: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ArticleResponse](../../com.jetpack.kawanusaha.data/-article-response/index.md)
