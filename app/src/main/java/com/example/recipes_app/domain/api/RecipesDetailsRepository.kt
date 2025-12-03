package com.example.recipes_app.domain.api


import com.example.recipes_app.domain.models.RecipeDetails
import com.example.recipes_app.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface RecipesDetailsRepository {
    fun getDetails(id: Int): Flow<Resource<RecipeDetails>>
}