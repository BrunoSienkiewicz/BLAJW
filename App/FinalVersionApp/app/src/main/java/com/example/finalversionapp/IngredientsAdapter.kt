package com.example.finalversionapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(private val ingredients: MutableList<Ingredient>) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.stepTextView
        val quantityTextView: TextView = view.ingredientQuantityTextView
        val removeButton: Button = view.selectButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.nameTextView.text = ingredient.name
        holder.quantityTextView.text = ingredient.quantity.toString()
        holder.removeButton.setOnClickListener {
            ingredients.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, ingredients.size)
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}


