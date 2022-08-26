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
import com.burakcanduzcan.currencylisterx.util.Globals
import com.google.android.material.tabs.TabLayout
import com.prof.rssparser.Article
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UpdatesFragment : Fragment() {

    private var _binding: FragmentUpdatesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UpdatesViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpdatesBinding.inflate(inflater)

        Timber.i("UpdatesFragment onCreateView")
        initializeViews()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Timber.i("UpdatesFragment onResume")
        Timber.i("Last clicked tab is ${Globals.NEWSSOURCE}")
        autoSelectTabItem()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        //tabLayout
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (binding.tabLayout.selectedTabPosition) {
                    0 -> {
                        Timber.i("News tab selected: Cointelegraph")
                        Globals.NEWSSOURCE = "Cointelegraph"
                        viewModel.getLatestNews()
                    }
                    1 -> {
                        Timber.i("News tab selected: CoinDesk")
                        Globals.NEWSSOURCE = "CoinDesk"
                        viewModel.getLatestNews()
                    }
                    2 -> {
                        Timber.i("News tab selected: CoinJournal")
                        Globals.NEWSSOURCE = "CoinJournal"
                        viewModel.getLatestNews()
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

    private fun autoSelectTabItem() {
        when (Globals.NEWSSOURCE) {
            "Cointelegraph" -> {
                binding.tabLayout.getTabAt(0)!!.select()
            }
            "CoinDesk" -> {
                binding.tabLayout.getTabAt(1)!!.select()
            }
            "CoinJournal" -> {
                binding.tabLayout.getTabAt(2)!!.select()
            }
        }
    }
}