package com.burakcanduzcan.currencylisterx.data.remote

import com.burakcanduzcan.currencylisterx.model.CryptoCoin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoService {
    @GET("/api/v3/coins/markets?order=market_cap_desc&sparkline=true")
    suspend fun fetchCoinMarketData(
        @Query("vs_currency") currency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): Response<List<CryptoCoin>>

    //...
}