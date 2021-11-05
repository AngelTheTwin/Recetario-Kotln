package com.example.recetario

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.data.db.AppDataBase
import com.example.recetario.data.entity.Receta
import com.example.recetario.recycler.RecetaAdapter
import kotlinx.android.synthetic.main.activity_lista_recetas.*

class ListaRecetas : AppCompatActivity() {
    private var arrayRecetas : ArrayList<Receta> = ArrayList()
    var categoria = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_recetas)
        val bundle = intent.extras
        categoria = bundle?.getString("categoria") ?: ""
        llenarLista()
    }


    private fun llenarLista() {
        arrayRecetas.clear()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecetas)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val database = AppDataBase.getDataBase(this)
        database.recetas().getAllByCategoria(categoria).observe(this, Observer {
            for (receta in it){
                arrayRecetas.add(receta)
            }

            actualizarLista()
        })
    }

    private fun actualizarLista(){
        recyclerViewRecetas.adapter = RecetaAdapter(arrayRecetas)
    }


}