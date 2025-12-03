package com.example.recipes_app.domain.models

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val code: Int) : Resource<T>
}