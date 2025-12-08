package com.example.recipes_app.data.network.api

import com.example.recipes_app.data.network.dto.Response

interface NetworkClient {

    suspend fun doRequest(dto: Any): Response

}