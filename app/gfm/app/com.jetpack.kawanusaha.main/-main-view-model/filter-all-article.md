//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[MainViewModel](index.md)/[filterAllArticle](filter-all-article.md)

# filterAllArticle

[androidJvm]\
fun [filterAllArticle](filter-all-article.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), category: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../../com.jetpack.kawanusaha.data/-articles-item/index.md)&gt;&gt;

Filters articles based on the given text and category. If the category is 0, it searches all articles using the text. Otherwise, it filters articles based on the selected category.

#### Return

A Flow of PagingData containing the filtered articles.

#### Parameters

androidJvm

| | |
|---|---|
| text | The text to search for. |
| category | The category ID to filter by. |
