package com.capstone.sampahin.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat
import com.capstone.sampahin.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private const val MAXIMAL_SIZE = 1000000
private val timeStamp : String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun createCustomTempFile(context: Context): File{
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun uriToFile(imageUri: Uri, context: Context): File{
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun File.reduceFileImage(): File{
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))


    return file
}

fun getProfileIcon(context: Context, isLocalUser: Boolean): Drawable {
    val drawable =
        ContextCompat.getDrawable(context, R.drawable.mascot)
            ?: throw IllegalStateException("Could not get user profile image")

    if (isLocalUser) {
        ContextCompat.getDrawable(context, R.drawable.baseline_account_circle_24)
    } else {
        ContextCompat.getDrawable(context, R.drawable.mascot)
    }

    return drawable
}