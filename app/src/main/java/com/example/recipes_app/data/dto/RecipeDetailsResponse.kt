package com.example.recipes_app.data.dto

data class RecipeDetailsResponse(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val instructions: String,
    val extendedIngredients: List<ExtendedIngredientDto>,
    val readyInMinutes: Int,
    val servings: Int
) : Response()
