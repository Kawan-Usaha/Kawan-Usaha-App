//[app](../../index.md)/[com.jetpack.kawanusaha.ui.pages](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [NavigationItem](-navigation-item/index.md) | [androidJvm]<br>data class [NavigationItem](-navigation-item/index.md)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val icon: [ImageVector](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector.html), val screen: [Screen](-screen/index.md)) |
| [Screen](-screen/index.md) | [androidJvm]<br>sealed class [Screen](-screen/index.md) |

## Functions

| Name | Summary |
|---|---|
| [BackPressHandler](-back-press-handler.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [BackPressHandler](-back-press-handler.md)(backPressedDispatcher: [OnBackPressedDispatcher](https://developer.android.com/reference/kotlin/androidx/activity/OnBackPressedDispatcher.html)? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher, onBackPressed: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [BottomBar](-bottom-bar.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [BottomBar](-bottom-bar.md)(navController: [NavController](https://developer.android.com/reference/kotlin/androidx/navigation/NavController.html), bottomBarState: [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;) |
| [LoginScreen](-login-screen.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [LoginScreen](-login-screen.md)(viewModel: [LoginViewModel](../com.jetpack.kawanusaha.main/-login-view-model/index.md), navToRegister: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), navToLanding: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), navToMain: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), navToForgotPassword: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [NavFabButton](-nav-fab-button.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NavFabButton](-nav-fab-button.md)(navigation: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [NavigationScreen](-navigation-screen.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NavigationScreen](-navigation-screen.md)(loginViewModel: [LoginViewModel](../com.jetpack.kawanusaha.main/-login-view-model/index.md), mainViewModel: [MainViewModel](../com.jetpack.kawanusaha.main/-main-view-model/index.md)) |
| [oauth](oauth.md) | [androidJvm]<br>fun [oauth](oauth.md)() |
| [OAuthButton](-o-auth-button.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [OAuthButton](-o-auth-button.md)() |
| [onKeyboardVisible](on-keyboard-visible.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [onKeyboardVisible](on-keyboard-visible.md)(): [State](https://developer.android.com/reference/kotlin/androidx/compose/runtime/State.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [scrollToBottom](scroll-to-bottom.md) | [androidJvm]<br>suspend fun [scrollToBottom](scroll-to-bottom.md)(state: [LazyListState](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/LazyListState.html)) |
| [scrollToItem](scroll-to-item.md) | [androidJvm]<br>suspend fun [scrollToItem](scroll-to-item.md)(state: [LazyListState](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/LazyListState.html), item: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [SectionText](-section-text.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [SectionText](-section-text.md)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), style: [TextStyle](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/TextStyle.html), modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier) |
| [shimmerBrush](shimmer-brush.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [shimmerBrush](shimmer-brush.md)(showShimmer: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, targetValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 1000.0f): [Brush](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Brush.html) |
| [TopBar](-top-bar.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [TopBar](-top-bar.md)(action: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
