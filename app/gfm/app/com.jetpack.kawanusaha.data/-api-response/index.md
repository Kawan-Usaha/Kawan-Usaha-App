//[app](../../../index.md)/[com.jetpack.kawanusaha.data](../index.md)/[ApiResponse](index.md)

# ApiResponse

sealed class [ApiResponse](index.md)&lt;out [R](index.md)&gt;

#### Inheritors

| |
|---|
| [Success](-success/index.md) |
| [Error](-error/index.md) |
| [Empty](-empty/index.md) |

## Types

| Name | Summary |
|---|---|
| [Empty](-empty/index.md) | [androidJvm]<br>object [Empty](-empty/index.md) : [ApiResponse](index.md)&lt;[Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)&gt; |
| [Error](-error/index.md) | [androidJvm]<br>data class [Error](-error/index.md)(val errorMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ApiResponse](index.md)&lt;[Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)&gt; |
| [Success](-success/index.md) | [androidJvm]<br>data class [Success](-success/index.md)&lt;out [T](-success/index.md)&gt;(val data: [T](-success/index.md)) : [ApiResponse](index.md)&lt;[T](-success/index.md)&gt; |
