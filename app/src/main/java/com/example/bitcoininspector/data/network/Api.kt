package com.example.bitcoininspector.data.network

import com.example.bitcoininspector.data.DTO.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET

interface Api {

  @GET("ticker")
  suspend fun getExchangeRate(): Response<ExchangeRateResponse>
}
