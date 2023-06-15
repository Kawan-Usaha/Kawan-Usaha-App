package com.jetpack.kawanusaha.tools

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.*

private const val MAXIMAL_SIZE = 1000000

/**
 * Reduces the file size of the given [file] by compressing it.
 * The compression is performed by adjusting the compress quality until the stream length is smaller than the [MAXIMAL_SIZE].
 * The function modifies the given file in-place and returns it.
 *
 * @param file The file to be reduced in size.
 * @return The modified file after compression.
 */
fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

/**
 * Retrieves a file from the given [uri] using the provided [context].
 *
 * @param context The context used to access content resolver and file operations.
 * @param uri The URI of the file to retrieve.
 * @return The file retrieved from the URI, or null if an error occurred.
 */
fun getFileFromUri(context: Context, uri: Uri): File? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val outputFile: File = createTempFile(context)

    if (inputStream != null) {
        try {
            val outputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(4 * 1024)
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            outputStream.close()
            return outputFile
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return null
}

/**
 * Creates a temporary file with the "image" prefix and ".jpg" extension in the specified storage directory.
 *
 * @param context The context used to access the external files directory.
 * @return The created temporary file.
 * @throws IOException if an I/O error occurs while creating the file.
 */
fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("image", ".jpg", storageDir)
}