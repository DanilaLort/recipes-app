package com.example.recipes_app.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipes_app.domain.api.RecipesPagingInteractor
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class MainActivityViewModel(
    private val recipesInteractor: RecipesPagingInteractor
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    val recipesPagingData: Flow<PagingData<Recipe>> =
        searchQuery.flatMapLatest { query ->
            recipesInteractor.getRecipesPagingFlow(
                text = query,
                sort = "sort" to "popularity",
                null
            ).map {
                Log.d("PagingData", "flatMapLatest{...}.map ViewModel")
                it
            }
        }
            .onStart { Log.d("PagingData", "direct flow started ViewModel") }
            .onEach { Log.d("PagingData", "direct flow emited ViewModel") }
            .cachedIn(viewModelScope)

    fun searchRecipes(query: String) {
        searchQuery.value = query
    }
}