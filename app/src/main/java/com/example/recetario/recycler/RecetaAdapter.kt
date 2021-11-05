package com.example.recetario.recycler

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.R
import com.example.recetario.RecetaActivity
import com.example.recetario.data.entity.Receta
import java.io.Serializable
import java.util.ArrayList

class RecetaAdapter (private val listaRecetas: ArrayList<Receta>) :
    RecyclerView.Adapter<RecetaAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_recetas, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = listaRecetas[position]
        holder.titleImage.setImageBitmap(currentItem.imagen)
        holder.textTitle.text = currentItem.nombre
        holder.addListener(currentItem)
    }

    override fun getItemCount(): Int {
        return listaRecetas.size
    }

    class MyViewHolder (itemView : View) :RecyclerView.ViewHolder(itemView){

        val titleImage : ImageView = itemView.findViewById(R.id.imgReceta)
        val textTitle : TextView = itemView.findViewById((R.id.txtTituloReceta))

        fun addListener(receta: Receta){
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context.applicationContext, RecetaActivity::class.java)
                intent.putExtra("receta", receta as Parcelable)
                context.startActivity(intent)
            }
        }
    }
}