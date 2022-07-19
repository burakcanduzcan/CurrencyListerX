package com.burakcanduzcan.currencylisterx.model

import com.google.gson.annotations.SerializedName

data class CryptoCoin(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("current_price")
    val currentPrice: Double = 0.0,
    @SerializedName("market_cap")
    val marketCap: Long = 0,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int = 0,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Long = 0,
    @SerializedName("total_volume")
    val totalVolume: Long = 0,
    @SerializedName("high_24h")
    val high24H: Double = 0.0,
    @SerializedName("low_24h")
    val low24H: Double = 0.0,
    @SerializedName("price_change_24h")
    val priceChange24H: Double = 0.0,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24H: Double = 0.0,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24H: Double = 0.0,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24H: Double = 0.0,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double = 0.0,
    @SerializedName("total_supply")
    val totalSupply: Double = 0.0,
    @SerializedName("max_supply")
    val maxSupply: Double = 0.0,
    @SerializedName("ath")
    val ath: Double = 0.0,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double = 0.0,
    @SerializedName("atl_date")
    val atlDate: String = "",
    @SerializedName("atl")
    val atl: Double = 0.0,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double = 0.0,
    @SerializedName("ath_date")
    val athDate: String = "",
    @SerializedName("last_updated")
    val lastUpdated: String = "",
    @SerializedName("roi")
    val roi: Roi? = null,
)