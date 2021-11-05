package com.example.recetario.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Ingrediente")
class Ingrediente (val nombre : String,
                   var cantidad : Double,
                   val unidad : String,
                   var idReceta: Int = 0,
                   @PrimaryKey(autoGenerate = true) var idIngrediente : Int = 0) : Serializable