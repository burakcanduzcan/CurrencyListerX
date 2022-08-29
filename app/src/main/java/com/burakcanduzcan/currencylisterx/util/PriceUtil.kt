package com.burakcanduzcan.currencylisterx.util

import kotlin.math.abs

object PriceUtil {
    fun updateDecimalPartOfPrice(price: String): String {
        if (price.substringAfter('.').length == 1) {
            return "${price}0"
        }
        return price
    }

    fun getPriceChangePercentageText(priceChangePercentage: Double): String {
        //absolute value
        val tmpPriceChangePercentage = abs(priceChangePercentage)
        //percentage
        var extraIntegerDigits = 0
        if (tmpPriceChangePercentage >= 10.0 && tmpPriceChangePercentage < 100.0) {
            extraIntegerDigits = 1
        } else if (tmpPriceChangePercentage >= 100.0) {
            extraIntegerDigits = 2
        }
        //show only first 3 digits of percentage, excluding the comma
        var tmpText: String = tmpPriceChangePercentage.toString()
        if (tmpText.length > 3) {
            tmpText = tmpText.dropLast(tmpText.length - (4 + extraIntegerDigits))
        }
        return tmpText
    }

    fun updatePriceTextWithCurrency(price: Double): String {
        return when (Globals.CURRENCY) {
            "USD" -> {
                "$${price}"
            }
            "EUR" -> {
                "€${price}"
            }
            "TRY" -> {
                "${price}₺"
            }
            else -> {
                "error"
            }
        }
    }
}