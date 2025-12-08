package com.example.recipes_app.data.network.dto

data class RecipesRequest(
    val options: Map<String, String>,
    val offset: Int
)
