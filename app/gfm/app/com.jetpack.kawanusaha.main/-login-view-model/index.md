//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[LoginViewModel](index.md)

# LoginViewModel

[androidJvm]\
class [LoginViewModel](index.md)(dataRepository: [DataRepository](../../com.jetpack.kawanusaha.data/-data-repository/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

## Constructors

| | |
|---|---|
| [LoginViewModel](-login-view-model.md) | [androidJvm]<br>constructor(dataRepository: [DataRepository](../../com.jetpack.kawanusaha.data/-data-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [generate](generate.md) | [androidJvm]<br>fun [generate](generate.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |
| [login](login.md) | [androidJvm]<br>fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [register](register.md) | [androidJvm]<br>fun [register](register.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), passwordConfirm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [verify](verify.md) | [androidJvm]<br>fun [verify](verify.md)(verification_code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, passwordConfirm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [isGenerated](is-generated.md) | [androidJvm]<br>val [isGenerated](is-generated.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [isLoading](is-loading.md) | [androidJvm]<br>val [isLoading](is-loading.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [isVerified](is-verified.md) | [androidJvm]<br>val [isVerified](is-verified.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [loginCredential](login-credential.md) | [androidJvm]<br>val [loginCredential](login-credential.md): StateFlow&lt;[LoginResponse](../../com.jetpack.kawanusaha.data/-login-response/index.md)?&gt; |
| [loginToken](login-token.md) | [androidJvm]<br>val [loginToken](login-token.md): StateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [registerCredential](register-credential.md) | [androidJvm]<br>val [registerCredential](register-credential.md): StateFlow&lt;[RegisterResponse](../../com.jetpack.kawanusaha.data/-register-response/index.md)?&gt; |
