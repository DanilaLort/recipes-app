package com.example.recipes_app.data.network

import com.example.recipes_app.data.dto.Response

interface NetworkClient {

    suspend fun doRequest(dto: Any): Response

}