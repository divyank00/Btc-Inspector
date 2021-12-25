package com.example.bitcoininspector.data.network

import retrofit2.Response

abstract class ApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody().toString()
            throw Exception(error)
        }
    }
}
