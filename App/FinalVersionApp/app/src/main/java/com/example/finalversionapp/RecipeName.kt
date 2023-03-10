package com.example.finalversionapp

import android.os.Parcel
import android.os.Parcelable

data class RecipeName(val id: Int,
                      val name: String,
                      val type: List<String>,
                      val time: Int,
                      val ingredients: List<Ingredient>,
                      val steps: List<String> ) : java.io.Serializable