package com.example.recipes_app.domain.impl

import com.example.recipes_app.domain.api.RecipesDetailsInteractor
import com.example.recipes_app.domain.api.RecipesDetailsRepository
import com.example.recipes_app.domain.models.RecipeDetails
import com.example.recipes_app.domain.models.Resource
import kotlinx.coroutines.flow.Flow

class RecipesDetailsInteractorImpl(
    private val recipesDetailsRepository: RecipesDetailsRepository
) : RecipesDetailsInteractor {

    override fun getDetails(id: Int): Flow<Resource<RecipeDetails>> {
        return recipesDetailsRepository.getDetails(id)
    }

}