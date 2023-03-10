package com.example.finalversionapp

data class Ingredient(val name: String, val quantity: Float): java.io.Serializable {
    companion object {
        fun fromString(string: String): Ingredient {
            val parts = string.replace("'","").split(" - ")
            val name = parts[0]
            val quantity = parts[1].filter { it.isDigit() }.toFloat()
            return Ingredient(name, quantity)
        }
    }
}
