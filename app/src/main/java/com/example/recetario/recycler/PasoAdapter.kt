package com.example.recetario.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recetario.R

class PasoAdapter (private val listaPasos : ArrayList<PasoData>) : RecyclerView.Adapter<PasoAdapter.MyHolderView>(){


    class MyHolderView (itemView : View) : RecyclerView.ViewHolder(itemView){
        var textNumeroPaso : TextView = itemView.findViewById(R.id.txtViewNumeroPaso)
        var textPaso : TextView = itemView.findViewById(R.id.txtViewPaso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_paso, parent, false)
        return MyHolderView(itemView)
    }

    override fun onBindViewHolder(holder: MyHolderView, position: Int) {
        val currentItem = listaPasos[position]
        holder.textNumeroPaso.text = currentItem.numeroPaso.toString()
        holder.textPaso.text = currentItem.paso
    }

    override fun getItemCount(): Int {
        return listaPasos.size
    }
}