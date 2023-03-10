package com.example.finalversionapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.type_item.view.*

class TypesAdapter(private val types: MutableList<String>) : RecyclerView.Adapter<TypesAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val typeTextView: TextView = view.stepTextView
        val removeButton: Button = view.selectButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.type_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val type = types[position]
        holder.typeTextView.text = type
        holder.removeButton.setOnClickListener {
            types.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, types.size)
        }
    }

    override fun getItemCount(): Int {
        return types.size
    }
}