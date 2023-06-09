//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[LoginViewModelFactory](index.md)

# LoginViewModelFactory

[androidJvm]\
class [LoginViewModelFactory](index.md)(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) : [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)

Factory class for creating instances of LoginViewModel.

## Constructors

| | |
|---|---|
| [LoginViewModelFactory](-login-view-model-factory.md) | [androidJvm]<br>constructor(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html)) |

## Functions

| Name | Summary |
|---|---|
| [create](../-main-view-model-factory/index.md#79759200%2FFunctions%2F-912451524) | [androidJvm]<br>open fun &lt;[T](../-main-view-model-factory/index.md#79759200%2FFunctions%2F-912451524) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](../-main-view-model-factory/index.md#79759200%2FFunctions%2F-912451524)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](../-main-view-model-factory/index.md#79759200%2FFunctions%2F-912451524)&gt;, extras: [CreationExtras](https://developer.android.com/reference/kotlin/androidx/lifecycle/viewmodel/CreationExtras.html)): [T](../-main-view-model-factory/index.md#79759200%2FFunctions%2F-912451524)<br>[androidJvm]<br>open override fun &lt;[T](create.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](create.md)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](create.md)&gt;): [T](create.md)<br>Creates an instance of the specified ViewModel class. |
