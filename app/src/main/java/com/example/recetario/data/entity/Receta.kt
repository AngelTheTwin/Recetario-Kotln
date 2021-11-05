package com.example.recetario.data.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "Receta")
@Parcelize
class Receta(
    val nombre: String,
    val preparacion: String,
    val numeroPersonas: Int,
    val enlaceVideo: String,
    val categoria: String,
    var estado: String = "activo",
    var imagen: Bitmap?,
    @PrimaryKey(autoGenerate = true) var idReceta: Int = 0) : Serializable, Parcelable