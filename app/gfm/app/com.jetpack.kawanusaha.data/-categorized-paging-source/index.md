//[app](../../../index.md)/[com.jetpack.kawanusaha.data](../index.md)/[CategorizedPagingSource](index.md)

# CategorizedPagingSource

[androidJvm]\
class [CategorizedPagingSource](index.md)(apiService: [ApiService](../../com.jetpack.kawanusaha.network/-api-service/index.md), id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [PagingSource](https://developer.android.com/reference/kotlin/androidx/paging/PagingSource.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), [ArticlesItem](../-articles-item/index.md)&gt;

## Constructors

| | |
|---|---|
| [CategorizedPagingSource](-categorized-paging-source.md) | [androidJvm]<br>constructor(apiService: [ApiService](../../com.jetpack.kawanusaha.network/-api-service/index.md), id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [getRefreshKey](get-refresh-key.md) | [androidJvm]<br>open override fun [getRefreshKey](get-refresh-key.md)(state: [PagingState](https://developer.android.com/reference/kotlin/androidx/paging/PagingState.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), [ArticlesItem](../-articles-item/index.md)&gt;): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? |
| [invalidate](index.md#1106667736%2FFunctions%2F-912451524) | [androidJvm]<br>fun [invalidate](index.md#1106667736%2FFunctions%2F-912451524)() |
| [load](load.md) | [androidJvm]<br>open suspend override fun [load](load.md)(params: [PagingSource.LoadParams](https://developer.android.com/reference/kotlin/androidx/paging/PagingSource.LoadParams.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;): [PagingSource.LoadResult](https://developer.android.com/reference/kotlin/androidx/paging/PagingSource.LoadResult.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), [ArticlesItem](../-articles-item/index.md)&gt; |
| [registerInvalidatedCallback](index.md#-395603798%2FFunctions%2F-912451524) | [androidJvm]<br>fun [registerInvalidatedCallback](index.md#-395603798%2FFunctions%2F-912451524)(onInvalidatedCallback: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [unregisterInvalidatedCallback](index.md#814983601%2FFunctions%2F-912451524) | [androidJvm]<br>fun [unregisterInvalidatedCallback](index.md#814983601%2FFunctions%2F-912451524)(onInvalidatedCallback: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [invalid](index.md#-162919568%2FProperties%2F-912451524) | [androidJvm]<br>val [invalid](index.md#-162919568%2FProperties%2F-912451524): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [jumpingSupported](index.md#242546777%2FProperties%2F-912451524) | [androidJvm]<br>open val [jumpingSupported](index.md#242546777%2FProperties%2F-912451524): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [keyReuseSupported](index.md#-439827890%2FProperties%2F-912451524) | [androidJvm]<br>open val [keyReuseSupported](index.md#-439827890%2FProperties%2F-912451524): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
