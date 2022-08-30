package com.burakcanduzcan.currencylisterx.util

object Globals {
    var CURRENCY: String = "USD"
    var NEWSSOURCE: String = "Cointelegraph"
    var IS_MARKET_FAVORITE_ON = false
    var FAVORITE_LIST = arrayListOf<String>()

    fun generateFavoriteListString(): String {
        var tmpString = ""
        for (i in 0 until FAVORITE_LIST.size) {
            if (i == 0) {
                tmpString = tmpString + FAVORITE_LIST[i]
            } else {
                tmpString = tmpString + "," + FAVORITE_LIST[i]
            }
        }
        return tmpString
    }

    fun buildFavoriteListFromString(savedText: String) {
        if (savedText.isNotEmpty()) {
            FAVORITE_LIST = ArrayList(savedText.split(','))
        }
    }

}