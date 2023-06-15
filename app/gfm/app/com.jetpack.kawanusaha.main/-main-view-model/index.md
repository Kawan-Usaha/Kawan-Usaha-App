//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[MainViewModel](index.md)

# MainViewModel

[androidJvm]\
class [MainViewModel](index.md)(dataRepository: [DataRepository](../../com.jetpack.kawanusaha.data/-data-repository/index.md), preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), application: [Application](https://developer.android.com/reference/kotlin/android/app/Application.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel class for everything in the system.

## Constructors

| | |
|---|---|
| [MainViewModel](-main-view-model.md) | [androidJvm]<br>constructor(dataRepository: [DataRepository](../../com.jetpack.kawanusaha.data/-data-repository/index.md), preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), application: [Application](https://developer.android.com/reference/kotlin/android/app/Application.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [clearArticleCache](clear-article-cache.md) | [androidJvm]<br>fun [clearArticleCache](clear-article-cache.md)()<br>Clears the article cache by setting it to null. |
| [clearCache](clear-cache.md) | [androidJvm]<br>fun [clearCache](clear-cache.md)()<br>Clears the LLM response cache by resetting it to the default initial message. |
| [clearImage](clear-image.md) | [androidJvm]<br>fun [clearImage](clear-image.md)()<br>Clears the image file value by setting it to a null Uri. |
| [clearStatus](clear-status.md) | [androidJvm]<br>fun [clearStatus](clear-status.md)()<br>Clears the status value by setting it to false. |
| [createArticle](create-article.md) | [androidJvm]<br>fun [createArticle](create-article.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), content: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), category: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Creates a new article with the provided title, content, and category. If an image file is specified, it is included in the multipart request. If no image file is specified, the article is created without an image. |
| [createUsaha](create-usaha.md) | [androidJvm]<br>fun [createUsaha](create-usaha.md)(usahaName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), tags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Tag](../../com.jetpack.kawanusaha.data/-tag/index.md)&gt;)<br>Creates a new business. |
| [filterAllArticle](filter-all-article.md) | [androidJvm]<br>fun [filterAllArticle](filter-all-article.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), category: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../../com.jetpack.kawanusaha.data/-articles-item/index.md)&gt;&gt;<br>Filters articles based on the given text and category. If the category is 0, it searches all articles using the text. Otherwise, it filters articles based on the selected category. |
| [getArticleDetail](get-article-detail.md) | [androidJvm]<br>fun [getArticleDetail](get-article-detail.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Retrieves the article detail for the specified ID. |
| [getFavourite](get-favourite.md) | [androidJvm]<br>fun [getFavourite](get-favourite.md)()<br>Retrieves the list of favorite articles. |
| [getListUsaha](get-list-usaha.md) | [androidJvm]<br>fun [getListUsaha](get-list-usaha.md)()<br>Retrieves the list of businesses. |
| [getUsahaDetail](get-usaha-detail.md) | [androidJvm]<br>fun [getUsahaDetail](get-usaha-detail.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Retrieves the details of a specific business. |
| [getUser](get-user.md) | [androidJvm]<br>fun [getUser](get-user.md)()<br>Retrieves the user profile from the data repository and updates the `_userProfile` FlowState. |
| [getUserArticles](get-user-articles.md) | [androidJvm]<br>fun [getUserArticles](get-user-articles.md)(): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../../com.jetpack.kawanusaha.data/-articles-item/index.md)&gt;&gt;<br>Retrieves the user articles using a Flow of PagingData. |
| [saveArticleCache](save-article-cache.md) | [androidJvm]<br>fun [saveArticleCache](save-article-cache.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), category: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Saves the article cache with the provided title, description, and category. |
| [saveProfileChange](save-profile-change.md) | [androidJvm]<br>fun [saveProfileChange](save-profile-change.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Saves the profile changes by updating the user's name and email. |
| [selectThisCategory](select-this-category.md) | [androidJvm]<br>fun [selectThisCategory](select-this-category.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Sets the selected category. |
| [sendMsg](send-msg.md) | [androidJvm]<br>fun [sendMsg](send-msg.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Sends a message from the user to the chat stream. |
| [setFavourite](set-favourite.md) | [androidJvm]<br>fun [setFavourite](set-favourite.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Sets the specified article as favorite. |
| [setImage](set-image.md) | [androidJvm]<br>fun [setImage](set-image.md)(uri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html))<br>Sets the image file with the provided URI. |

## Properties

| Name | Summary |
|---|---|
| [articleCache](article-cache.md) | [androidJvm]<br>val [articleCache](article-cache.md): StateFlow&lt;[CreateArticleRequest](../../com.jetpack.kawanusaha.data/-create-article-request/index.md)?&gt; |
| [articleDetail](article-detail.md) | [androidJvm]<br>val [articleDetail](article-detail.md): StateFlow&lt;[ArticleDetail](../../com.jetpack.kawanusaha.data/-article-detail/index.md)?&gt; |
| [categoryList](category-list.md) | [androidJvm]<br>val [categoryList](category-list.md): StateFlow&lt;[CategoryResponse](../../com.jetpack.kawanusaha.data/-category-response/index.md)?&gt; |
| [favouriteList](favourite-list.md) | [androidJvm]<br>val [favouriteList](favourite-list.md): StateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ArticlesItem](../../com.jetpack.kawanusaha.data/-articles-item/index.md)&gt;?&gt; |
| [imageFile](image-file.md) | [androidJvm]<br>val [imageFile](image-file.md): StateFlow&lt;[Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)&gt; |
| [isLoading](is-loading.md) | [androidJvm]<br>val [isLoading](is-loading.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [listUsaha](list-usaha.md) | [androidJvm]<br>val [listUsaha](list-usaha.md): StateFlow&lt;[UsahaResponse](../../com.jetpack.kawanusaha.data/-usaha-response/index.md)?&gt; |
| [llmResponse](llm-response.md) | [androidJvm]<br>val [llmResponse](llm-response.md): StateFlow&lt;[ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[Message](../../com.jetpack.kawanusaha.data/-message/index.md)&gt;&gt; |
| [selectedCategory](selected-category.md) | [androidJvm]<br>val [selectedCategory](selected-category.md): StateFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [status](status.md) | [androidJvm]<br>val [status](status.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [stringResponse](string-response.md) | [androidJvm]<br>val [stringResponse](string-response.md): StateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [tagList](tag-list.md) | [androidJvm]<br>val [tagList](tag-list.md): StateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TagItem](../../com.jetpack.kawanusaha.data/-tag-item/index.md)&gt;?&gt; |
| [usahaDetail](usaha-detail.md) | [androidJvm]<br>val [usahaDetail](usaha-detail.md): StateFlow&lt;[UsahaDetailResponse](../../com.jetpack.kawanusaha.data/-usaha-detail-response/index.md)?&gt; |
| [userProfile](user-profile.md) | [androidJvm]<br>val [userProfile](user-profile.md): StateFlow&lt;[ProfileResponse](../../com.jetpack.kawanusaha.data/-profile-response/index.md)?&gt; |
