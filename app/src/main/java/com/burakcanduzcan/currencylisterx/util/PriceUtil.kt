package com.burakcanduzcan.currencylisterx.util

object PriceUtil {
    fun updateDecimalPartOfPrice(price: String): String {
        if (price.substringAfter('.').length == 1) {
            return "${price}0"
        }
        return price
    }
}