package com.burakcanduzcan.currencylisterx.ui.updates

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.currencylisterx.databinding.FragmentUpdatesBinding
import com.google.android.material.tabs.TabLayout
import com.prof.rssparser.Article
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UpdatesFragment : Fragment() {

    private lateinit var binding: FragmentUpdatesBinding
    private val viewModel: UpdatesViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUpdatesBinding.inflate(inflater)

        Timber.i("UpdatesFragment onCreateView")
        initializeViews()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Timber.i("UpdatesFragment onResume")
    }

    private fun initializeViews() {
        //tabLayout
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (binding.tabLayout.selectedTabPosition) {
                    0 -> {
                        Timber.i("News tab selected: Cointelegraph")
                        viewModel.getLatestNews("Cointelegraph")
                    }
                    1 -> {
                        Timber.i("News tab selected: CoinDesk")
                        viewModel.getLatestNews("CoinDesk")
                    }
                    2 -> {
                        Timber.i("News tab selected: CoinJournal")
                        viewModel.getLatestNews("CoinJournal")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        //recyclerView
        binding.rvNews.apply {
            newsAdapter = NewsAdapter(::onNewsClick)
            this.adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        //observe
        viewModel.articleList.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            //updateProgressBar()
        }
    }

    private fun onNewsClick(article: Article) {
        val url = article.link
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}