package com.example.recipes_app.domain.impl

import androidx.paging.PagingData
import com.example.recipes_app.domain.api.RecipesPagingInteractor
import com.example.recipes_app.domain.api.RecipesPagingRepository
import com.example.recipes_app.domain.models.FilterSettings
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

class RecipesPagingInteractorImpl(
    private val recipesRepository: RecipesPagingRepository
) : RecipesPagingInteractor {

    override fun getRecipesPagingFlow(filterSettings: FilterSettings): Flow<PagingData<Recipe>> {
        return recipesRepository.getRecipesPagingFlow(
            filterSettings.toQueryMap()
        )
    }

    fun FilterSettings.toQueryMap(): Map<String, String> {
        val options = mutableMapOf<String, String>()
        options.addIfNotEmpty("query", searchQuery)
        options.addIfNotNull("type", selectedType)
        options.addIfNotDefault("sort", sortBy, "popularity")
        options.addIfNotDefault("sortDirection", sortDirection, "desc")
        return options
    }

    private fun MutableMap<String, String>.addIfNotEmpty(
        key: String,
        value: String
    ) {
        if (value.isNotBlank()) {
            put(key, value)
        }
    }

    private fun MutableMap<String, String>.addIfNotNull(
        key: String,
        value: String?
    ) {
        value?.let { put(key, it) }
    }

    private fun MutableMap<String, String>.addIfNotDefault(
        key: String,
        value: String,
        defaultValue: String
    ) {
        if (value != defaultValue) {
            put(key, value)
        }
    }

}