package com.example.finalversionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_chosen_recipe.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chosen_ingredient_item.*
import kotlinx.android.synthetic.main.chosen_steps_item.*
import kotlin.math.roundToInt

class chosenRecipeActivity : AppCompatActivity() {

    private lateinit var stepsViewModel: StepsViewModel
    private lateinit var stepsAdapter: StepsAdapter
    private lateinit var chosenIngredientsViewModel: ChosenIngredientsViewModel
    private lateinit var chosenIngredientsAdapter: ChosenIngredientsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_recipe)

        stepsViewModel = ViewModelProvider(this)[StepsViewModel::class.java]
        stepsAdapter = StepsAdapter(stepsViewModel.steps)
        chosenIngredientsViewModel = ViewModelProvider(this)[ChosenIngredientsViewModel::class.java]
        chosenIngredientsAdapter = ChosenIngredientsAdapter(chosenIngredientsViewModel.ingredients)

        ingredientsRecyclerView.adapter = chosenIngredientsAdapter
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        stepsRecyclerView.adapter = stepsAdapter
        stepsRecyclerView.layoutManager = LinearLayoutManager(this)

        var ingredientsList: List<Ingredient> = ArrayList<Ingredient>()
        var stepsList: List<String> = ArrayList<String>()

        var recipe: RecipeName = this.getIntent().getSerializableExtra("recipe") as RecipeName
        var chosenIngredientsList: List<Ingredient> = this.getIntent().getParcelableArrayListExtra("ingredients")!!

        recipeNameEditText.text = String.format("%s czas przygotowania: %d min", recipe.name, recipe.time)

        ingredientsList = recipe.ingredients
        stepsList = recipe.steps

        var minRatio: Float = 99999F

        if (chosenIngredientsList.isEmpty()){
            minRatio = 1F
        }

        for (chosenIngredient in chosenIngredientsList){
            for (ingredient in ingredientsList){
                var recipeIngredientNames = mutableListOf<String>()
                val names = ingredient.name.lowercase().split(" ")
                for (name in names) {
                    recipeIngredientNames.add(name)
                }
                if (chosenIngredient.name.lowercase() in recipeIngredientNames){
                    var ratio: Float = (chosenIngredient.quantity/ingredient.quantity).toFloat()
                    if (chosenIngredient.quantity == 0F){
                        ratio = 1F
                    }
                    if (ratio < minRatio){
                        minRatio = ratio
                    }
                }
            }
        }

        var portions = Ingredient("Ilość porcji które możesz zrobić:", ((minRatio*100).roundToInt()/100).toFloat())
        chosenIngredientsViewModel.addIngredient(portions)
        chosenIngredientsAdapter.notifyItemInserted(chosenIngredientsViewModel.ingredients.size - 1)

        for (ingredient in ingredientsList) {
            chosenIngredientsViewModel.addIngredient(
                Ingredient(
                    ingredient.name,
                    ((ingredient.quantity * minRatio * 100).roundToInt()/100).toFloat()
                )
            )
            chosenIngredientsAdapter.notifyItemInserted(chosenIngredientsViewModel.ingredients.size - 1)
        }

        for (step in stepsList) {
            step.replace("'","")
            stepsViewModel.addStep(step)
            stepsAdapter.notifyItemInserted(stepsViewModel.steps.size - 1)
        }
    }
}