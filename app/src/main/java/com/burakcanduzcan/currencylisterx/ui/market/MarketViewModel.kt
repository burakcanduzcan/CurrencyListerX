package com.burakcanduzcan.currencylisterx.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakcanduzcan.currencylisterx.data.repository.CryptoCoinRepository
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.Globals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val cryptoCoinRepository: CryptoCoinRepository,
) : ViewModel() {

    private val _coinListLiveData = MutableLiveData<List<CryptoCoinUiModel>>()
    val coinListLiveData: LiveData<List<CryptoCoinUiModel>> = _coinListLiveData

    //GET request parameters (currency is in util/PriceUtil.kt)
    private var _perPage: Int = 50
    val perPage: Int get() = _perPage

    private var _page: Int = 1
    val page: Int get() = _page

    //data state
    private var _isDataSet: Boolean = false
    val isDataSet: Boolean get() = _isDataSet

    init {
        getCurrentCryptoCoins()
        _isDataSet = true
    }

    private fun getCurrentCryptoCoins() {
        viewModelScope.launch {
            _coinListLiveData.value =
                cryptoCoinRepository.getCoinListFromCoinGeckoAPI(Globals.CURRENCY, perPage, page)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isDataSet = false
            getCurrentCryptoCoins()
            _isDataSet = true
        }
    }

    fun getFavoriteCoins() {
        viewModelScope.launch {
            //hold whole coins in a temporary list
            val tmpList = ArrayList<CryptoCoinUiModel>()
            //filter
            for (coin in cryptoCoinRepository.getCoinListFromCoinGeckoAPI(Globals.CURRENCY, perPage, page)) {
                if (Globals.FAVORITE_LIST.contains(coin.symbol.lowercase())) {
                    tmpList.add(coin)
                }
            }
            //give list that contains ONLY favorite coins to live data
            _coinListLiveData.value = tmpList
        }
    }
}