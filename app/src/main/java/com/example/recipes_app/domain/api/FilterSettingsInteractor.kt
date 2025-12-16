package com.example.recipes_app.domain.api

import com.example.recipes_app.domain.models.FilterSettings
import kotlinx.coroutines.flow.Flow

interface FilterSettingsInteractor {
    suspend fun updateSearchQuery(query: String)
    suspend fun updateSort(sortBy: String)
    suspend fun updateSortType(sortType: String)
    suspend fun updateSortDirection(sortDirection: String)
    suspend fun clearAllFilters()
    fun getCurrentSettings(): Flow<FilterSettings>
}