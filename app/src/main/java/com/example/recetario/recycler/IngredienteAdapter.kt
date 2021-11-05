package com.example.recetario.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.R
import com.example.recetario.RecetaActivity
import com.example.recetario.data.entity.Ingrediente

class IngredienteAdapter (private val listaIngredientes: ArrayList<Ingrediente>) : RecyclerView.Adapter<IngredienteAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_ingrediente, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = listaIngredientes[position]
        holder.nombreIngrediente.text = currentItem.nombre
        holder.cantidad.text = currentItem.cantidad.toString()
        holder.unidad.text = currentItem.unidad
    }

    override fun getItemCount(): Int {
        return listaIngredientes.size
    }

    class MyViewHolder (itemView : View)  : RecyclerView.ViewHolder(itemView){
        val nombreIngrediente : TextView = itemView.findViewById(R.id.txtViewIngrediente)
        val cantidad : TextView = itemView.findViewById(R.id.txtViewCantidad)
        val unidad : TextView = itemView.findViewById(R.id.txtViewUnidad)

        fun getIngrediente () : Ingrediente {
            return Ingrediente(
                nombreIngrediente.text.toString(),
                cantidad.text.toString().toDouble(),
                unidad.text.toString())
        }
    }
}