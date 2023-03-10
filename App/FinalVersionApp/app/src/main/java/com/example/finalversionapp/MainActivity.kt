package com.example.finalversionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.opencsv.CSVReader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.handleCoroutineException

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: IngredientsViewModel
    private lateinit var adapter: IngredientsAdapter
    private lateinit var typesViewModel: TypesViewModel
    private lateinit var typesAdapter: TypesAdapter
    private val recipes = mutableListOf<RecipeName>()
    private val ingredients = mutableListOf<Ingredient>()
    private val types = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = assets
        val inputStream = manager.open("database.csv")
        val reader = CSVReader(inputStream.reader())
        val records = reader.readAll()
        for (record in records) {
            if(record[0] == "id" || record[0] == ""){
                continue
            }
            val id = record[0].toInt()
            val name = record[1]
            val temp_types = record[2].replace("[","").replace("]","")
            val types = temp_types.split(", ").toMutableList()
            for (i in types.indices){
                val newType = types[i].replace("'","")
                types[i] = newType
            }
            val time = record[3].toInt()
            val temp_ingredients = record[4].replace("[","").replace("]","")
            val ingredients = temp_ingredients.split(", ").map { Ingredient.fromString(it) }
            val steps = record[5].replace("[","").replace("]","").split(", ")
            recipes.add(RecipeName(id, name, types, time, ingredients, steps))
        }



        viewModel = ViewModelProvider(this)[IngredientsViewModel::class.java]
        adapter = IngredientsAdapter(viewModel.ingredients)
        ingredientRecyclerView.adapter = adapter
        ingredientRecyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val name = ingredientNameEditText.text.toString()
            var quantity: Float
            quantity = try {
                ingredientQuantityEditText.text.toString().toFloat()
            } catch (e: java.lang.NumberFormatException) {
                0F
            }

            ingredients.add(Ingredient(name, quantity))
            viewModel.addIngredient(Ingredient(name, quantity))
            adapter.notifyItemInserted(viewModel.ingredients.size - 1)
            ingredientNameEditText.text.clear()
            ingredientQuantityEditText.text.clear()
        }

        typesViewModel = ViewModelProvider(this)[TypesViewModel::class.java]
        typesAdapter = TypesAdapter(typesViewModel.types)
        typesRecyclerView.adapter = typesAdapter
        typesRecyclerView.layoutManager = LinearLayoutManager(this)

        addTypeButton.setOnClickListener {
            val type = mealTypeEditText.text.toString()
            types.add(type)
            typesViewModel.addType(type)
            typesAdapter.notifyItemInserted(typesViewModel.types.size - 1)
            mealTypeEditText.text.clear()
        }

        suggestRecipesButton.setOnClickListener {
            val intent = Intent(this, RecipesActivity::class.java)
            intent.putParcelableArrayListExtra("recipes", recipes as java.util.ArrayList<out Parcelable>)
            intent.putParcelableArrayListExtra("ingredients", ingredients as java.util.ArrayList<out Parcelable>)
            intent.putParcelableArrayListExtra("types", types as java.util.ArrayList<out Parcelable>)
            recreate()
            startActivity(intent)
        }
    }}


