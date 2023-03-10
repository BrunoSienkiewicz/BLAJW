package com.example.finalversionapp

import androidx.lifecycle.ViewModel

class IngredientsViewModel : ViewModel() {

    val ingredients = mutableListOf<Ingredient>()

    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }
    fun removeIngredient(ingredient: Ingredient) {
        ingredients.remove(ingredient)
    }
}

