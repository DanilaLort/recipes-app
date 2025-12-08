package com.example.recipes_app.domain.models

data class FilterSettings(
    val searchQuery: String = "",
    val selectedType: String? = null,
    val sortBy: String = "popularity",
    val sortDirection: String = "desc",
)
