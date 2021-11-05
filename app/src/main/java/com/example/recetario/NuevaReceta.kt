package com.example.recetario

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recetario.data.db.AppDataBase
import com.example.recetario.data.entity.Ingrediente
import com.example.recetario.data.entity.Receta
import com.example.recetario.recycler.IngredienteAdapter
import com.example.recetario.recycler.PasoAdapter
import com.example.recetario.recycler.PasoData
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_nueva_receta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevaReceta : AppCompatActivity() {

    private var contadorIngredientes = 0
    private var contadorPasos = 0
    private val pasosData = ArrayList<PasoData>()
    private val ingredientesData = ArrayList<Ingrediente>()
    private var isImageSelected = false
    var idReceta : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_receta)
        llenarSpinners()
        agregarListeners()
    }


    private fun llenarSpinners(){
        val spinnerUnidades = findViewById<Spinner>(R.id.spinnerUnidades)
        val spinnerCategoria = findViewById<Spinner>(R.id.spinnerCategoria)

        val unidades = arrayOf<String>(
            "gr",
            "ml",
            "cda",
            "cdita",
            "tz",
            "pz"
        )
        val adapterUnidades = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unidades)
        spinnerUnidades.adapter = adapterUnidades

        val categorias = arrayOf<String>(
            "Entrada",
            "Plato fuerte",
            "Postre",
            "Bebida"
        )
        val adapterCategorias = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias)
        spinnerCategoria.adapter = adapterCategorias
    }

    private fun agregarListeners(){
        btnGuardar.setOnClickListener {
            val database = AppDataBase.getDataBase2(this)


            if(validarCampos()){
                CoroutineScope(Dispatchers.IO).launch {
                    idReceta = database.recetas().getLastId() +1
                }


                val nombreReceta = txtNombreReceta.text.toString()
                val numeroPersonas = txtNumeroPersonas.text.toString().toInt()
                val categoria = spinnerCategoria.selectedItem.toString()
                val link = if (txtLink.text.isNotEmpty())  txtLink.text.toString() else ""

                var preparacion = ""

                for (paso in pasosData){
                    preparacion += "${paso.numeroPaso}. ${paso.paso} \n"
                }

                val nuevaReceta = Receta(nombreReceta, preparacion, numeroPersonas, link, categoria, "activo", getBitmap())

                val ingredientes = ArrayList<Ingrediente>()

                for (i in 0 until recyclerViewIngredientes.childCount) {
                    val holder : IngredienteAdapter.MyViewHolder = recyclerViewIngredientes.findViewHolderForAdapterPosition(i) as IngredienteAdapter.MyViewHolder
                    val ingrediente = holder.getIngrediente()
                    ingrediente.idReceta = idReceta
                    ingredientes.add(ingrediente)
                }

                val context = this
                CoroutineScope(Dispatchers.IO).launch {
                    database.recetas().insertAll(nuevaReceta)
                    for (ingrediente in ingredientes){
                        database.ingredientes().insertAll(ingrediente)
                    }

                }
                Toast.makeText(context, "Registro realizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Asegurese de llenar los campos y/o que el link sea correcto", Toast.LENGTH_SHORT).show()
            }


        }

        btnAgregarIngrediente.setOnClickListener {
            if(validarCamposIngredientes()) {
                val nombreIngrediente = txtNombreIngrediente.text.toString()
                val cantidad = txtCantidad.text.toString().toDouble()
                val unidad = spinnerUnidades.selectedItem.toString()

                val nuevoIngrediente = Ingrediente(nombreIngrediente, cantidad, unidad)
                ingredientesData.add(nuevoIngrediente)
                llenarRecyclerIngredientes()
                limpiarCamposIngredientes()
                contadorIngredientes++

            } else {
                Toast.makeText(this, "Llene los datos del ingrediente", Toast.LENGTH_SHORT).show()
            }
        }

        btnAgregarPaso.setOnClickListener {
            if(validarCamposPaso()){
                contadorPasos++
                var nuevoPaso = PasoData(contadorPasos, txtPaso.text.toString())
                pasosData.add(nuevoPaso)
                llenarRecyclerPasos()
                limpiarCamposPaso()
            } else {
                Toast.makeText(this, "Asegúrese de llenar el campo de Paso", Toast.LENGTH_SHORT).show()
            }
        }

        btnSeleccionarImagen.setOnClickListener {
            ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*")).start()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode  == ImagePicker.REQUEST_CODE){
            imgViewImagenSeleccionada.setImageURI(data?.data)
            isImageSelected = true
        }
    }

    private fun getBitmap() : Bitmap{
        return imgViewImagenSeleccionada.drawable.toBitmap()
    }

    private fun validarCampos() : Boolean{
        return txtNombreReceta.text.isNotEmpty() &&
                txtNumeroPersonas.text.isNotEmpty() &&
                contadorIngredientes > 0 &&
                contadorPasos > 0 &&
                isLinkValid()
                && isImageSelected
    }

    private fun isLinkValid() : Boolean{
        return (txtLink.text.isEmpty() ||
                Patterns.WEB_URL.matcher(txtLink.text.toString()).matches())
    }

    private fun validarCamposIngredientes() : Boolean{
        return txtNombreIngrediente.text.isNotEmpty() &&
                txtCantidad.text.isNotEmpty()
    }

    private fun validarCamposPaso() : Boolean{
        return txtPaso.text.isNotEmpty()
    }

    private fun limpiarCamposReceta(){
        txtNombreReceta.text.clear()
        txtNumeroPersonas.text.clear()
        txtLink.text.clear()
    }

    private fun limpiarCamposIngredientes() {
        txtNombreIngrediente.text.clear()
        txtCantidad.text.clear()
    }

    private fun limpiarCamposPaso(){
        txtPaso.text.clear()
    }

    private fun llenarRecyclerIngredientes(){
        recyclerViewIngredientes.layoutManager = LinearLayoutManager(this)
        recyclerViewIngredientes.setHasFixedSize(true)

        recyclerViewIngredientes.adapter = IngredienteAdapter(ingredientesData)
    }

    private fun llenarRecyclerPasos() {
        recyclerViewPasos.layoutManager = LinearLayoutManager(this)
        recyclerViewPasos.setHasFixedSize(true)

        recyclerViewPasos.adapter = PasoAdapter(pasosData)
    }
}

