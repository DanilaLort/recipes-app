package com.example.recipes_app.domain.impl

import com.example.recipes_app.domain.api.FilterSettingsInteractor
import com.example.recipes_app.domain.api.FilterSettingsRepository
import com.example.recipes_app.domain.models.FilterSettings
import kotlinx.coroutines.flow.Flow

class FilterSettingsInteractorImpl(
    private val filterSettingsRepository: FilterSettingsRepository
) : FilterSettingsInteractor {

    override suspend fun updateSearchQuery(query: String) {
        filterSettingsRepository.updateSearchQuery(query)
    }

    override suspend fun updateSort(sortBy: String) {
        filterSettingsRepository.updateSort(sortBy)
    }

    override suspend fun updateSortType(sortType: String) {
        filterSettingsRepository.updateSortType(sortType)
    }

    override suspend fun updateSortDirection(sortDirection: String) {
        filterSettingsRepository.updateSortDirection(sortDirection)
    }

    override suspend fun clearAllFilters() {
        filterSettingsRepository.clearAllFilters()
    }

    override fun getCurrentSettings(): Flow<FilterSettings> = filterSettingsRepository.filterSettings

}
