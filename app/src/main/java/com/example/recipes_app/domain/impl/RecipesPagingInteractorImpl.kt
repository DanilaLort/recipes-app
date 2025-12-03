package com.example.recipes_app.domain.impl

import androidx.paging.PagingData
import com.example.recipes_app.domain.api.RecipesPagingInteractor
import com.example.recipes_app.domain.api.RecipesPagingRepository
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

class RecipesPagingInteractorImpl(
    private val recipesRepository: RecipesPagingRepository
) : RecipesPagingInteractor {

    override fun getRecipesPagingFlow(
        text: String?,
        sort: Pair<String, String>,
        type: Pair<String, String>?
    ): Flow<PagingData<Recipe>> {
        val options = mutableMapOf<String, String>(sort)
        if (type != null) options.put(type.first, type.second)
        if (text != null) options.put("query", text)
        return recipesRepository.getRecipesPagingFlow(options)
    }

}