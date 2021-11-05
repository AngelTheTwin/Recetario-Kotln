package com.example.recetario.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recetario.data.entity.Ingrediente
import com.example.recetario.data.entity.Receta

@Dao
interface IngredienteDao {
    @Query("SELECT * FROM Ingrediente")
    fun getAll() : LiveData<List<Ingrediente>>

    @Query("SELECT * FROM Ingrediente WHERE idReceta = :idReceta")
    fun getAllByIdReceta(idReceta : Int) : LiveData<List<Ingrediente>>

    @Query("SELECT * FROM Ingrediente WHERE idIngrediente = :id")
    fun get(id : Int) : LiveData<Ingrediente>

    @Insert
    fun insertAll(vararg  ingrediente: Ingrediente)

    @Query("Select MAX(idIngrediente) FROM Ingrediente")
    fun getLastId() : Int

    @Update
    fun update(ingrediente: Ingrediente)
}