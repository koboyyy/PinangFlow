package com.pinangflow.app.data.remote

import retrofit2.http.GET

// Placeholder for real API
interface PinangApiService {
    @GET("market/price")
    suspend fun getTodayMarketPrice(): Double
}
