package com.burakcanduzcan.currencylisterx.data.repository

import com.burakcanduzcan.currencylisterx.data.remote.CoinGeckoService
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import javax.inject.Inject

class CryptoCoinRepository @Inject constructor(
    private val coinGeckoService: CoinGeckoService,
) {
    suspend fun getCoinListFromCoinGeckoAPI(
        currency: String,
        perPage: Int,
        page: Int,
        order: String = "market_cap",
    ): List<CryptoCoinUiModel> {
        return coinGeckoService.fetchCoinMarketData(currency, order, perPage, page).body()!!.map {
            //mapping response body to simpler data model
            CryptoCoinUiModel(
                it.id,
                it.symbol,
                it.name,
                it.image,
                it.currentPrice,
                it.high24H,
                it.low24H,
                it.priceChangePercentage24H,
                it.sparklineIn7D.price
            )
        }
    }

}