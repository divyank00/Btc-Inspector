package com.example.bitcoininspector.data.Repository

import com.example.bitcoininspector.data.network.Api
import com.example.bitcoininspector.data.network.ApiRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityRepository
@Inject
constructor(
    private val api: Api
) : ApiRequest() {
    suspend fun getExchangeRate(): Double? {
        return try {
            val response = apiRequest { api.getExchangeRate() }
            response.uSD?._15m!!
        } catch (e: Exception) {
            null
        }
    }
}