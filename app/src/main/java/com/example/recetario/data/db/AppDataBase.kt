package com.example.recetario.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recetario.Converter
import com.example.recetario.data.entity.Ingrediente
import com.example.recetario.data.dao.IngredienteDao
import com.example.recetario.data.dao.RecetaDao
import com.example.recetario.data.entity.Receta

@Database(entities = [Receta::class, Ingrediente::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun recetas() : RecetaDao
    abstract fun ingredientes() : IngredienteDao

    companion object{
        @Volatile
        private var INSTANCE : AppDataBase? = null

        fun getDataBase(context: Context) : AppDataBase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }

        fun getDataBase2 (context: Context) : AppDataBase {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java, "app_database"
            ).build()
            return db
        }
    }
}