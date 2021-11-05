package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addListeners()
    }

    fun addListeners(){
        val buttonEntradas = findViewById<ImageButton>(R.id.btnEntradas)
        val buttonPlatosFuertes = findViewById<ImageButton>(R.id.btnPlatosFuertes)
        val buttonPostres = findViewById<ImageButton>(R.id.btnPostres)
        val buttonBebidas = findViewById<ImageButton>(R.id.btnBebidas)
        val buttonNuevaReceta = findViewById<Button>(R.id.btnNuevaReceta)


        buttonEntradas.setOnClickListener{
            val entradasIntent = Intent(this, ListaRecetas::class.java)
            entradasIntent.putExtra("categoria", "Entrada")
            startActivity(entradasIntent)
        }

        buttonPlatosFuertes.setOnClickListener{
            val platosFuertesIntent = Intent(this, ListaRecetas::class.java)
            platosFuertesIntent.putExtra("categoria", "Plato fuerte")
            startActivity(platosFuertesIntent)
        }

        buttonPostres.setOnClickListener {
            val postresIntent = Intent(this, ListaRecetas::class.java)
            postresIntent.putExtra("categoria", "Postre")
            startActivity(postresIntent)
        }

        buttonBebidas.setOnClickListener {
            val bebidasIntent = Intent(this, ListaRecetas::class.java)
            bebidasIntent.putExtra("categoria", "Bebida")
            startActivity(bebidasIntent)
        }

        buttonNuevaReceta.setOnClickListener {
            val nuevaRecetaIntent = Intent(this, NuevaReceta::class.java)
            startActivity(nuevaRecetaIntent)
        }
    }
}