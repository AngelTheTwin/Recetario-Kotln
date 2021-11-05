package com.example.recetario.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.recetario.data.entity.Receta

@Dao
interface RecetaDao {
    @Query("SELECT * FROM Receta WHERE estado = 'activo'")
    fun getAll(): LiveData<List<Receta>>

    @Query("SELECT * FROM Receta WHERE estado = 'activo' and categoria = :categoria")
    fun getAllByCategoria(categoria : String) : LiveData<List<Receta>>

    @Query("SELECT * FROM Receta WHERE idReceta = :id")
    fun get(id: Int): LiveData<Receta>

    @Query("SELECT MAX(idReceta) FROM Receta")
    fun getLastId() : Int

    @Insert
    fun insertAll(vararg receta: Receta)

    @Update
    fun update(receta: Receta)
}