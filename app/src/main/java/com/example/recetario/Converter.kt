package com.example.recetario

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converter {

    @TypeConverter
    fun bitmapToByteArray(bitmap: Bitmap) : ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return  outputStream.toByteArray()
    }

    @TypeConverter
    fun byteArraytoBitmap(byteArray: ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}