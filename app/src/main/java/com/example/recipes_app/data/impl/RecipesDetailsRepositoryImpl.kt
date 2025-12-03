package com.example.recipes_app.data.impl


import com.example.recipes_app.data.dto.ExtendedIngredientDto
import com.example.recipes_app.data.dto.RecipeDetailsRequest
import com.example.recipes_app.data.dto.RecipeDetailsResponse
import com.example.recipes_app.data.network.NetworkClient
import com.example.recipes_app.domain.api.RecipesDetailsRepository
import com.example.recipes_app.domain.models.ExtendedIngredient
import com.example.recipes_app.domain.models.RecipeDetails
import com.example.recipes_app.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipesDetailsRepositoryImpl(
    private val networkClient: NetworkClient
) : RecipesDetailsRepository {

    companion object {
        private const val SUCCESSFUL_REQUEST = 200
    }

    override fun getDetails(id: Int): Flow<Resource<RecipeDetails>> = flow {
        val result = networkClient.doRequest(RecipeDetailsRequest(id))
        if (result.resultCode == SUCCESSFUL_REQUEST) {
            emit(
                Resource.Success(
                    (result as RecipeDetailsResponse).toDomain()
                )
            )
        } else {
            emit(
                Resource.Error(result.resultCode)
            )
        }
    }

    fun RecipeDetailsResponse.toDomain(): RecipeDetails {
        return RecipeDetails(
            this.id,
            this.title,
            this.image,
            this.summary,
            this.instructions,
            this.extendedIngredients.map { it.toDomain() },
            this.readyInMinutes,
            this.servings
        )
    }

    fun ExtendedIngredientDto.toDomain(): ExtendedIngredient {
        return ExtendedIngredient(
            this.name,
            this.original,
            this.image
        )
    }
}