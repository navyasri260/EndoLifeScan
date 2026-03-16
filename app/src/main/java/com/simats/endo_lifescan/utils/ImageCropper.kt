package com.simats.endo_lifescan.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

object ImageCropper {

    fun cropCenter(imageFile: File): File {

        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

        val width = bitmap.width
        val height = bitmap.height

        val cropSize = (width * 0.5).toInt()

        val startX = (width - cropSize) / 2
        val startY = (height - cropSize) / 2

        val croppedBitmap = Bitmap.createBitmap(
            bitmap,
            startX,
            startY,
            cropSize,
            cropSize
        )

        val croppedFile = File(imageFile.parent, "cropped_${imageFile.name}")

        croppedFile.outputStream().use {
            croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)
        }

        return croppedFile
    }
}