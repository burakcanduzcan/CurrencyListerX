package com.burakcanduzcan.currencylisterx.ui.updates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.burakcanduzcan.currencylisterx.util.Globals
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatesViewModel @Inject constructor(
    private val parser: Parser,
) : ViewModel() {

    private val _articleList = MutableLiveData<List<Article>>()
    val articleList: LiveData<List<Article>> = _articleList

    init {
        getLatestNews()
    }

    fun getLatestNews() {
        viewModelScope.launch(Dispatchers.IO) {
            when (Globals.NEWSSOURCE) {
                "Cointelegraph" -> {
                    val channel: Channel =
                        parser.getChannel(url = "https://cointelegraph.com/rss")
                    _articleList.postValue(channel.articles)
                }
                "CoinDesk" -> {
                    val channel: Channel =
                        parser.getChannel(url = "https://www.coindesk.com/arc/outboundfeeds/rss/")
                    _articleList.postValue(channel.articles)
                }
                "CoinJournal" -> {
                    val channel: Channel =
                        parser.getChannel(url = "https://coinjournal.net/news/category/analysis/feed/")
                    _articleList.postValue(channel.articles)
                }
            }
        }
    }
}