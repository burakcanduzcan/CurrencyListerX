package com.burakcanduzcan.currencylisterx.data.network

import com.burakcanduzcan.currencylisterx.model.CryptoCoin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {
    //order="market_cap" can also be given as parameter
    @GET("/api/v3/coins/markets?order=market_cap_desc&sparkline=false")
    suspend fun fetchCoinList(
        @Query("vs_currency") currency: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): Response<List<CryptoCoin>>
}