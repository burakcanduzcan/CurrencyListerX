package com.burakcanduzcan.currencylisterx.model

data class CryptoCoinUiModel(
    val id: String = "",
    val symbol: String = "",
    val name: String = "",
    val image: String = "",
    val currentPrice: Double = 0.0,
    val priceChangePercentage24H: Double = 0.0,
)