package com.burakcanduzcan.currencylisterx.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burakcanduzcan.currencylisterx.data.repository.CryptoCoinRepository
import com.burakcanduzcan.currencylisterx.model.CryptoCoin

class MarketViewModel : ViewModel() {
    private var _currentCurrency: String = "try"
    fun getCurrency(): String = _currentCurrency

    private var _coinListLiveData: MutableLiveData<List<CryptoCoin>> =
        CryptoCoinRepository.getCoinListFromCoinGeckoAPI(getCurrency(), 10, 1)
    val coinListLiveData: LiveData<List<CryptoCoin>> = _coinListLiveData
}