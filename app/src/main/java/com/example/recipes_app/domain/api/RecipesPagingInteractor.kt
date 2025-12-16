package com.example.recipes_app.domain.api

import androidx.paging.PagingData
import com.example.recipes_app.domain.models.FilterSettings
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesPagingInteractor {
    fun getRecipesPagingFlow(filterSettings: FilterSettings): Flow<PagingData<Recipe>>
}