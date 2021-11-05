package com.example.recetario

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetario.data.db.AppDataBase
import com.example.recetario.data.entity.Ingrediente
import com.example.recetario.data.entity.Receta
import com.example.recetario.recycler.IngredienteAdapter
import kotlinx.android.synthetic.main.activity_receta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecetaActivity : AppCompatActivity() {
    lateinit var receta : Receta
    var numeroPersonasActual : Int = 0
    val listIngredientes = ArrayList<Ingrediente>()

    override fun onCreate(savedInstanceState: Bundle?) {
        receta = intent.getParcelableExtra<Receta>("receta")!!
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receta)
        llenarDatos()
    }

    private fun llenarDatos(){
        numeroPersonasActual = receta.numeroPersonas
        txtCalcularNumeroPersonas.setText(receta.numeroPersonas.toString())
        imgFotoReceta.setImageBitmap(receta.imagen)
        txtReceta.text = receta.nombre
        txtViewProcedimiento.text = receta.preparacion
        txtViewCategoria.text = receta.categoria


        llenarRecyclerInrgedientes()
        agregarListeners()
    }

    private fun agregarListeners() {
        if (receta.enlaceVideo != "") {
            buttonEnlace.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(receta.enlaceVideo))
                startActivity(intent)
            }
        } else {
            buttonEnlace.isEnabled = false
        }

        btnEliminarReceta.setOnClickListener {
            val context = this
            AlertDialog.Builder(this).apply {
                setMessage("¿Seguro que desea eliminar la receta?")
                setPositiveButton("Si", ){_: DialogInterface, _: Int ->
                    val database = AppDataBase.getDataBase(context)
                    receta.estado = "inactivo"
                    CoroutineScope(Dispatchers.IO).launch {
                        database.recetas().update(receta)
                    }

                    Toast.makeText(context, "Receta Eliminada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
                setNegativeButton("No", null)
            }.show()


        }

        btnCalcular.setOnClickListener {
            if (validarCampos()) {
                val numeroPersonas = txtCalcularNumeroPersonas.text.toString().toInt()
                for (ingrediente in listIngredientes) {
                    val cantidad = (ingrediente.cantidad / numeroPersonasActual ) * numeroPersonas
                    ingrediente.cantidad = cantidad
                }

                numeroPersonasActual = numeroPersonas

                actualizarRecycler()
            } else {
                Toast.makeText(this, "El campo Numero de personas no debe estar vacio.", Toast.LENGTH_SHORT).show()
            }
            
        }
    }
    
    private fun validarCampos() : Boolean {
        return txtCalcularNumeroPersonas.text.isNotEmpty()
    }

    private fun llenarRecyclerInrgedientes(){
        recyclerIngredientes.layoutManager = LinearLayoutManager(this)

        val dataBase = AppDataBase.getDataBase(this)

        dataBase.ingredientes().getAllByIdReceta(receta.idReceta).observe(this, Observer {

            for (ingrediente in it){
                listIngredientes.add(ingrediente)
            }

            actualizarRecycler()
        })
    }

    private fun actualizarRecycler() {
        recyclerIngredientes.adapter = IngredienteAdapter(listIngredientes)
    }
}