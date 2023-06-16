//[app](../../index.md)/[com.jetpack.kawanusaha.tools](index.md)/[getFileFromUri](get-file-from-uri.md)

# getFileFromUri

[androidJvm]\
fun [getFileFromUri](get-file-from-uri.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), uri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)?

Retrieves a file from the given [uri](get-file-from-uri.md) using the provided [context](get-file-from-uri.md).

#### Return

The file retrieved from the URI, or null if an error occurred.

#### Parameters

androidJvm

| | |
|---|---|
| context | The context used to access content resolver and file operations. |
| uri | The URI of the file to retrieve. |
