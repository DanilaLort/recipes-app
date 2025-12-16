package com.example.recipes_app.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipes_app.domain.api.FilterSettingsInteractor
import com.example.recipes_app.domain.api.RecipesPagingInteractor
import com.example.recipes_app.domain.models.FilterSettings
import com.example.recipes_app.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val recipesInteractor: RecipesPagingInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private val filterSettingsLiveData: LiveData<FilterSettings> = MutableLiveData()
    private val searchQuery = MutableStateFlow("")

    val recipesPagingData: Flow<PagingData<Recipe>> =
        searchQuery.flatMapLatest { query ->
            val filterSettings = filterSettingsInteractor.getCurrentSettings().first()
            Log.d("PagingData", "$filterSettings")

            recipesInteractor.getRecipesPagingFlow(filterSettings).map {
                Log.d("PagingData", "flatMapLatest{...}.map ViewModel")
                it
            }
        }
            .onStart { Log.d("PagingData", "direct flow started ViewModel") }
            .onEach { Log.d("PagingData", "direct flow emited ViewModel") }
            .cachedIn(viewModelScope)


    fun setRecipesQuery(query: String) {
        searchQuery.value = query
        viewModelScope.launch {
            filterSettingsInteractor.updateSearchQuery(query)
        }
    }

    fun setFilters(
        sortBy: String = "popularity",
        sortType: String,
        sortDirection: String = "desc"
    ) {
        viewModelScope.launch {
            filterSettingsInteractor.updateSort(sortBy.lowercase())
            filterSettingsInteractor.updateSortType(sortType.lowercase())
            filterSettingsInteractor.updateSortDirection(sortDirection.lowercase())
        }
    }


}