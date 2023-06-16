//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[LoginViewModel](index.md)

# LoginViewModel

[androidJvm]\
class [LoginViewModel](index.md)(dataRepository: [DataRepository](../../com.jetpack.kawanusaha.data/-data-repository/index.md), preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

*Authentication Functions*

This class every logics to authenticate users to the application

## Constructors

| | |
|---|---|
| [LoginViewModel](-login-view-model.md) | [androidJvm]<br>constructor(dataRepository: [DataRepository](../../com.jetpack.kawanusaha.data/-data-repository/index.md), preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../-main-view-model/index.md#264516373%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../-main-view-model/index.md#264516373%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [clear](clear.md) | [androidJvm]<br>fun [clear](clear.md)() |
| [generate](generate.md) | [androidJvm]<br>fun [generate](generate.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Generate a token and send it to user email address |
| [isLoggedIn](is-logged-in.md) | [androidJvm]<br>fun [isLoggedIn](is-logged-in.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check the login status |
| [login](login.md) | [androidJvm]<br>fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Authenticate user and check for its credentials User credential will be saved in loginCredential variable Session Created |
| [logout](logout.md) | [androidJvm]<br>fun [logout](logout.md)()<br>Clear session and token from preferences |
| [register](register.md) | [androidJvm]<br>fun [register](register.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), passwordConfirm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Create a new account for new user. User credential will be saved in registerCredential variable Session Created |
| [verify](verify.md) | [androidJvm]<br>fun [verify](verify.md)(verification_code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, passwordConfirm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Verify the token inputted by user |

## Properties

| Name | Summary |
|---|---|
| [isVerified](is-verified.md) | [androidJvm]<br>val [isVerified](is-verified.md): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [loginCredential](login-credential.md) | [androidJvm]<br>val [loginCredential](login-credential.md): StateFlow&lt;[LoginResponse](../../com.jetpack.kawanusaha.data/-login-response/index.md)?&gt; |
| [message](message.md) | [androidJvm]<br>val [message](message.md): StateFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [registerCredential](register-credential.md) | [androidJvm]<br>val [registerCredential](register-credential.md): StateFlow&lt;[RegisterResponse](../../com.jetpack.kawanusaha.data/-register-response/index.md)?&gt; |
