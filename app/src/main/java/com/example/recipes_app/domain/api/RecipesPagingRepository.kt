package com.example.recipes_app.domain.api

import androidx.paging.PagingData
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesPagingRepository {
    fun getRecipesPagingFlow(options: Map<String, String>): Flow<PagingData<Recipe>>
}