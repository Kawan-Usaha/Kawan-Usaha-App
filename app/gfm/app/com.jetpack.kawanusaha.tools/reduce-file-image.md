//[app](../../index.md)/[com.jetpack.kawanusaha.tools](index.md)/[reduceFileImage](reduce-file-image.md)

# reduceFileImage

[androidJvm]\
fun [reduceFileImage](reduce-file-image.md)(file: [File](https://developer.android.com/reference/kotlin/java/io/File.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)

Reduces the file size of the given [file](reduce-file-image.md) by compressing it. The compression is performed by adjusting the compress quality until the stream length is smaller than the MAXIMAL_SIZE. The function modifies the given file in-place and returns it.

#### Return

The modified file after compression.

#### Parameters

androidJvm

| | |
|---|---|
| file | The file to be reduced in size. |
