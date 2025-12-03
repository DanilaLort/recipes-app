package com.example.recipes_app.data.impl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipes_app.data.network.RecipesApi
import com.example.recipes_app.domain.api.RecipesPagingRepository
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class RecipesPagingRepositoryImpl(
    private val api: RecipesApi
    ) : RecipesPagingRepository {
    override fun getRecipesPagingFlow(options: Map<String, String>): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 10,
                enablePlaceholders = false,
                initialLoadSize = 40,
                maxSize = 200
            ),
            pagingSourceFactory = {
                RecipesPagingSource(api, options)
            }
        ).flow
            .onStart { Log.d("PagingData", "pager started") }
            .onEach{ Log.d("PagingData", "pager emit") }
    }
}