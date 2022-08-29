package com.burakcanduzcan.currencylisterx.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoCoinUiModel(
    val id: String = "",
    val symbol: String = "",
    val name: String = "",
    val image: String = "",
    val currentPrice: Double = 0.0,
    val high24H: Double = 0.0,
    val low24H: Double = 0.0,
    val priceChangePercentage24H: Double = 0.0,
    val sparklineIn7D: List<Double>?
) : Parcelable