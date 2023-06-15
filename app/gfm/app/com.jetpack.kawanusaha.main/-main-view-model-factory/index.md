//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[MainViewModelFactory](index.md)

# MainViewModelFactory

class [MainViewModelFactory](index.md)(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), application: [Application](https://developer.android.com/reference/kotlin/android/app/Application.html)) : [ViewModelProvider.Factory](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModelProvider.Factory.html)

Factory class for creating instances of MainViewModel.

#### Parameters

androidJvm

| | |
|---|---|
| preferences | The SharedPreferences instance for accessing preferences. |
| application | The Application instance. |

## Constructors

| | |
|---|---|
| [MainViewModelFactory](-main-view-model-factory.md) | [androidJvm]<br>constructor(preferences: [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html), application: [Application](https://developer.android.com/reference/kotlin/android/app/Application.html)) |

## Functions

| Name | Summary |
|---|---|
| [create](index.md#79759200%2FFunctions%2F-912451524) | [androidJvm]<br>open fun &lt;[T](index.md#79759200%2FFunctions%2F-912451524) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](index.md#79759200%2FFunctions%2F-912451524)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](index.md#79759200%2FFunctions%2F-912451524)&gt;, extras: [CreationExtras](https://developer.android.com/reference/kotlin/androidx/lifecycle/viewmodel/CreationExtras.html)): [T](index.md#79759200%2FFunctions%2F-912451524)<br>open override fun &lt;[T](create.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](create.md)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](create.md)&gt;): [T](create.md) |
