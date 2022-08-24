package com.burakcanduzcan.currencylisterx.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakcanduzcan.currencylisterx.data.repository.CryptoCoinRepository
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.PriceUtil
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
    private var _perPage: Int = 20
    val perPage: Int
        get() = _perPage

    private var _page: Int = 1
    val page: Int
        get() = _page

    //data state
    private var _isDataSet: Boolean = false
    val isDataSet: Boolean
        get() = _isDataSet

    init {
        getCurrentCryptoCoins()
        _isDataSet = true
    }

    private fun getCurrentCryptoCoins() {
        viewModelScope.launch {
            _coinListLiveData.value =
                cryptoCoinRepository.getCoinListFromCoinGeckoAPI(PriceUtil.CURRENCY, perPage, page)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isDataSet = false
            getCurrentCryptoCoins()
            _isDataSet = true
        }
    }
}