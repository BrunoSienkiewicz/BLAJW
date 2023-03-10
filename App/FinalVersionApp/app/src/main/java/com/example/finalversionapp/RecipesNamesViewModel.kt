package com.example.finalversionapp

import androidx.lifecycle.ViewModel

class RecipesNamesViewModel : ViewModel() {

    val recipesNames = mutableListOf<RecipeName>()

    fun addRecipeName(recipeName: RecipeName) {
        recipesNames.add(recipeName)
    }
}