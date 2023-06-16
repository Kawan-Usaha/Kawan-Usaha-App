//[app](../../index.md)/[com.jetpack.kawanusaha.tools](index.md)/[createTempFile](create-temp-file.md)

# createTempFile

[androidJvm]\
fun [createTempFile](create-temp-file.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)

Creates a temporary file with the &quot;image&quot; prefix and &quot;.jpg&quot; extension in the specified storage directory.

#### Return

The created temporary file.

#### Parameters

androidJvm

| | |
|---|---|
| context | The context used to access the external files directory. |

#### Throws

| | |
|---|---|
| [IOException](https://developer.android.com/reference/kotlin/java/io/IOException.html) | if an I/O error occurs while creating the file. |
