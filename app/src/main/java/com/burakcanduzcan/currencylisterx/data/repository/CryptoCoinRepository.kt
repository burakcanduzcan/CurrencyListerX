package com.burakcanduzcan.currencylisterx.data.repository

import androidx.lifecycle.MutableLiveData
import com.burakcanduzcan.currencylisterx.data.network.RetrofitHelper
import com.burakcanduzcan.currencylisterx.model.CryptoCoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoCoinRepository {
    companion object {
        fun getCoinListFromCoinGeckoAPI(
            currency: String,
            perPage: Int,
            page: Int,
        ): MutableLiveData<List<CryptoCoin>> {
            val coinListLiveData = MutableLiveData<List<CryptoCoin>>()
            CoroutineScope(Dispatchers.Default).launch {
                launch(Dispatchers.IO) {
                    val response =
                        RetrofitHelper.coinGeckoApi.fetchCoinList(currency, perPage, page)
                    withContext(Dispatchers.Default) {
                        response.let {
                            if (response.isSuccessful) {
                                coinListLiveData.postValue(response.body())
                            }
                        }
                    }
                }
            }
            return coinListLiveData
        }
    }
}