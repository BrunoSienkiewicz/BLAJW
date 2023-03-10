package com.example.finalversionapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recipes.*
import kotlinx.android.synthetic.main.recipe_item.*

class RecipesActivity : AppCompatActivity() {

    private lateinit var viewModel :RecipesNamesViewModel
    private lateinit var adapter: RecipesNamesAdapter
    private val filteredRecipes = mutableListOf<RecipeName>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        var recipesList: List<RecipeName> = ArrayList<RecipeName>()
        recipesList = this.getIntent().getParcelableArrayListExtra("recipes")!!
        var ingredientsList: List<Ingredient> = ArrayList<Ingredient>()
        ingredientsList = this.getIntent().getParcelableArrayListExtra("ingredients")!!
        var typesList: List<String> = ArrayList<String>()
        typesList = this.getIntent().getParcelableArrayListExtra("types")!!


        if (ingredientsList.isNotEmpty()){
            for (ingredient in ingredientsList){
                for (recipe in recipesList){
                    var recipeIngredientNames = mutableListOf<String>()
                    for (ingredient in recipe.ingredients) {
                        val names = ingredient.name.lowercase().split(" ")
                        for (name in names){
                            recipeIngredientNames.add(name)
                        }
                    }
                    if (ingredient.name in recipeIngredientNames){
                        filteredRecipes.add(recipe)
                    }
                }
            }
        }else{
            for (recipe in recipesList){
                filteredRecipes.add(recipe)
            }
        }

        var typeFilteredRecipes = mutableListOf<RecipeName>()
        if (typesList.isNotEmpty()){
            for (type in typesList){
                for (recipe in filteredRecipes){
                    var recipeTypesNames = mutableListOf<String>()
                    for (recipe_type in recipe.type){
                        recipeTypesNames.add(recipe_type.lowercase())
                    }
                    if (type.lowercase() in recipeTypesNames){
                        typeFilteredRecipes.add(recipe)
                    }
                }
            }
        }else{
            for (recipe in filteredRecipes){
                typeFilteredRecipes.add(recipe)
            }
        }

        class MyRecipesNamesAdapter(public val context: Context): RecipesNamesAdapter(
            typeFilteredRecipes
        ){
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val recipeName = recipesNames[position]
                holder.nameTextView.text = recipeName.name
                holder.selectButton.setOnClickListener{
                    val intent = Intent(context, chosenRecipeActivity::class.java)
                    intent.putExtra("recipe", recipeName)
                    intent.putParcelableArrayListExtra("ingredients", ingredientsList as java.util.ArrayList<out Parcelable>)
                    recreate()
                    startActivity(intent)
                }
            }
        }

        viewModel = ViewModelProvider(this).get(RecipesNamesViewModel::class.java)
        adapter = MyRecipesNamesAdapter(this)

        for (recipe in typeFilteredRecipes) {
            viewModel.addRecipeName(
                RecipeName(
                    recipe.id,
                    recipe.name,
                    recipe.type,
                    recipe.time,
                    recipe.ingredients,
                    recipe.steps
                )
            )
            adapter.notifyItemInserted(viewModel.recipesNames.size - 1)
        }

        ingredientsRecyclerView.adapter = adapter
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}