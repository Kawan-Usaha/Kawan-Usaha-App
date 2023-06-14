//[app](../../../index.md)/[com.jetpack.kawanusaha.data](../index.md)/[DataRepository](index.md)

# DataRepository

[androidJvm]\
class [DataRepository](index.md)(apiService: [ApiService](../../com.jetpack.kawanusaha.network/-api-service/index.md))

A repository class for handling data operations.

## Constructors

| | |
|---|---|
| [DataRepository](-data-repository.md) | [androidJvm]<br>constructor(apiService: [ApiService](../../com.jetpack.kawanusaha.network/-api-service/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [chatResult](chat-result.md) | [androidJvm]<br>suspend fun [chatResult](chat-result.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), llmRequest: [LLMRequest](../-l-l-m-request/index.md)): [LLMResponse](../-l-l-m-response/index.md)?<br>Chat Section : Batch (Currently unused) |
| [createArticle](create-article.md) | [androidJvm]<br>suspend fun [createArticle](create-article.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), imageMultipart: MultipartBody.Part?, createArticleRequest: [CreateArticleRequest](../-create-article-request/index.md)): [DefaultResponse](../-default-response/index.md)? |
| [createUsaha](create-usaha.md) | [androidJvm]<br>suspend fun [createUsaha](create-usaha.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), usahaRequest: [UsahaRequest](../-usaha-request/index.md)): [DefaultResponse](../-default-response/index.md)? |
| [forgotGenerate](forgot-generate.md) | [androidJvm]<br>suspend fun [forgotGenerate](forgot-generate.md)(forgotGenerateRequest: [ForgotGenerateRequest](../-forgot-generate-request/index.md)): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
| [forgotVerify](forgot-verify.md) | [androidJvm]<br>suspend fun [forgotVerify](forgot-verify.md)(forgotVerifyRequest: [ForgotVerifyRequest](../-forgot-verify-request/index.md)): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
| [generate](generate.md) | [androidJvm]<br>suspend fun [generate](generate.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
| [getArticleDetail](get-article-detail.md) | [androidJvm]<br>suspend fun [getArticleDetail](get-article-detail.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ArticleDetailResponse](../-article-detail-response/index.md)? |
| [getCategorizedArticles](get-categorized-articles.md) | [androidJvm]<br>fun [getCategorizedArticles](get-categorized-articles.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../-articles-item/index.md)&gt;&gt; |
| [getCategory](get-category.md) | [androidJvm]<br>suspend fun [getCategory](get-category.md)(): [CategoryResponse](../-category-response/index.md)?<br>Category & Tag Section |
| [getFavourite](get-favourite.md) | [androidJvm]<br>suspend fun [getFavourite](get-favourite.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [FavResponse](../-fav-response/index.md)? |
| [getListArticle](get-list-article.md) | [androidJvm]<br>fun [getListArticle](get-list-article.md)(): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../-articles-item/index.md)&gt;&gt;<br>Article Section |
| [getListUsaha](get-list-usaha.md) | [androidJvm]<br>suspend fun [getListUsaha](get-list-usaha.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UsahaResponse](../-usaha-response/index.md)?<br>Usaha Section |
| [getTag](get-tag.md) | [androidJvm]<br>suspend fun [getTag](get-tag.md)(): [TagResponse](../-tag-response/index.md)? |
| [getUsahaDetail](get-usaha-detail.md) | [androidJvm]<br>suspend fun [getUsahaDetail](get-usaha-detail.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [UsahaDetailResponse](../-usaha-detail-response/index.md)? |
| [getUser](get-user.md) | [androidJvm]<br>suspend fun [getUser](get-user.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ProfileResponse](../-profile-response/index.md)?<br>Profile Section |
| [getUserArticle](get-user-article.md) | [androidJvm]<br>fun [getUserArticle](get-user-article.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../-articles-item/index.md)&gt;&gt; |
| [login](login.md) | [androidJvm]<br>suspend fun [login](login.md)(loginRequest: [LoginRequest](../-login-request/index.md)): [LoginResponse](../-login-response/index.md)?<br>Authentication Section |
| [register](register.md) | [androidJvm]<br>suspend fun [register](register.md)(registerRequest: [RegisterRequest](../-register-request/index.md)): [RegisterResponse](../-register-response/index.md)? |
| [searchAllArticle](search-all-article.md) | [androidJvm]<br>fun [searchAllArticle](search-all-article.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[PagingData](https://developer.android.com/reference/kotlin/androidx/paging/PagingData.html)&lt;[ArticlesItem](../-articles-item/index.md)&gt;&gt; |
| [setFavourite](set-favourite.md) | [androidJvm]<br>suspend fun [setFavourite](set-favourite.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [DefaultResponse](../-default-response/index.md)?<br>Favourite Article Section |
| [updateProfile](update-profile.md) | [androidJvm]<br>suspend fun [updateProfile](update-profile.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), imageMultipart: MultipartBody.Part?, profileRequest: [ProfileRequest](../-profile-request/index.md)): [DefaultResponse](../-default-response/index.md)? |
| [verify](verify.md) | [androidJvm]<br>suspend fun [verify](verify.md)(jwtToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), verificationRequest: [VerificationRequest](../-verification-request/index.md)): [GenerateVerificationResponse](../-generate-verification-response/index.md)? |
