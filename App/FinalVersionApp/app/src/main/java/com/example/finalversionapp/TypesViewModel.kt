package com.example.finalversionapp

import androidx.lifecycle.ViewModel

class TypesViewModel : ViewModel() {

    val types = mutableListOf<String>()

    fun addType(type: String) {
        types.add(type)
    }
    fun removeType(type: String) {
        types.remove(type)
    }
}