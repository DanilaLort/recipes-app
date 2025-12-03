package com.example.recipes_app.domain.api

import androidx.paging.PagingData
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipesPagingInteractor {
    fun getRecipesPagingFlow(
        text: String?,
        sort: Pair<String, String>,
        type: Pair<String, String>?
    ): Flow<PagingData<Recipe>>
}