package com.example.finalversionapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipe_item.view.*

open class RecipesNamesAdapter(val recipesNames: MutableList<RecipeName>) : RecyclerView.Adapter<RecipesNamesAdapter.ViewHolder>(){

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.recipeNameTextView
        val selectButton: Button = view.selectButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeName = recipesNames[position]
        holder.nameTextView.text = recipeName.name
//        holder.
    }

    override fun getItemCount(): Int {
        return recipesNames.size

    }
}

