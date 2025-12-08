package com.example.recipes_app.data.network.dto

data class RecipeSearchResponse(
    val results: List<RecipeDto>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
) : Response()