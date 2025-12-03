package com.example.recipes_app.domain.models

data class RecipeDetails(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val instructions: String,
    val extendedIngredients: List<ExtendedIngredient>,
    val readyInMinutes: Int,
    val servings: Int
)
