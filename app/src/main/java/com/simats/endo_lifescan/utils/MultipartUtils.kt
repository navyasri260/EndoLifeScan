package com.simats.endo_lifescan.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun fileToMultipart(
    file: File,
    partName: String
): MultipartBody.Part {

    val requestBody = file
        .asRequestBody("image/*".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(
        partName,
        file.name,
        requestBody
    )
}