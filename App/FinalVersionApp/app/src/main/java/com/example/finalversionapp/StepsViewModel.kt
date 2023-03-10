package com.example.finalversionapp

import androidx.lifecycle.ViewModel

class StepsViewModel : ViewModel() {

    val steps = mutableListOf<String>()

    fun addStep(step: String) {
        steps.add(step)
    }
    fun removeStep(step: String) {
        steps.remove(step)
    }
}