package com.example.recipes_app.data.network

import com.example.recipes.BuildConfig
import com.example.recipes_app.data.dto.RecipeDetailsResponse
import com.example.recipes_app.data.dto.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipesApi {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap options: Map<String, String>,
        @Query("offset") offset: Int = 0,
        @Query("number") number: Int = 10,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ) : RecipeSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): RecipeDetailsResponse
}