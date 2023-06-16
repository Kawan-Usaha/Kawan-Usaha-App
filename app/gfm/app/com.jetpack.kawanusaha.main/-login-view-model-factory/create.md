//[app](../../../index.md)/[com.jetpack.kawanusaha.main](../index.md)/[LoginViewModelFactory](index.md)/[create](create.md)

# create

[androidJvm]\
open override fun &lt;[T](create.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)&gt; [create](create.md)(modelClass: [Class](https://developer.android.com/reference/kotlin/java/lang/Class.html)&lt;[T](create.md)&gt;): [T](create.md)

Creates an instance of the specified ViewModel class.

#### Return

An instance of the ViewModel.

#### Parameters

androidJvm

| | |
|---|---|
| modelClass | The class of the ViewModel to be created. |

#### Throws

| | |
|---|---|
| [IllegalArgumentException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/index.html) | if the specified ViewModel class is unknown. |
