package com.burakcanduzcan.currencylisterx.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val coinGeckoApi: CoinGeckoApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoApi::class.java)
    }
}
