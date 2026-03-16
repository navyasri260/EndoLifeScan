package com.simats.endo_lifescan.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Cannot open input stream")

    val file = File.createTempFile("image_", ".jpg", context.cacheDir)

    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)

    inputStream.close()
    outputStream.close()

    return file
}