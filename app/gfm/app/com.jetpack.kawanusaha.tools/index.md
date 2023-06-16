//[app](../../index.md)/[com.jetpack.kawanusaha.tools](index.md)

# Package-level declarations

## Functions

| Name | Summary |
|---|---|
| [createTempFile](create-temp-file.md) | [androidJvm]<br>fun [createTempFile](create-temp-file.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)<br>Creates a temporary file with the &quot;image&quot; prefix and &quot;.jpg&quot; extension in the specified storage directory. |
| [getFileFromUri](get-file-from-uri.md) | [androidJvm]<br>fun [getFileFromUri](get-file-from-uri.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), uri: [Uri](https://developer.android.com/reference/kotlin/android/net/Uri.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)?<br>Retrieves a file from the given [uri](get-file-from-uri.md) using the provided [context](get-file-from-uri.md). |
| [reduceFileImage](reduce-file-image.md) | [androidJvm]<br>fun [reduceFileImage](reduce-file-image.md)(file: [File](https://developer.android.com/reference/kotlin/java/io/File.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)<br>Reduces the file size of the given [file](reduce-file-image.md) by compressing it. The compression is performed by adjusting the compress quality until the stream length is smaller than the MAXIMAL_SIZE. The function modifies the given file in-place and returns it. |
