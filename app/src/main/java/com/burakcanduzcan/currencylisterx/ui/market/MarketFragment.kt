package com.burakcanduzcan.currencylisterx.ui.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.currencylisterx.databinding.FragmentMarketBinding
import com.burakcanduzcan.currencylisterx.model.CryptoCoin

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    private val viewModel: MarketViewModel by viewModels()
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMarketBinding.inflate(inflater)

        initializeViews()

        return binding.root
    }

    private fun initializeViews() {
        //observe
        binding.progressBar.isVisible = true
        viewModel.coinListLiveData.observe(viewLifecycleOwner) { cryptoCoinList ->
            coinListAdapter.submitList(cryptoCoinList)
        }
        binding.progressBar.isVisible = false

        binding.rvTodos.apply {
            coinListAdapter = CoinListAdapter(requireContext(), viewModel.getCurrency())
            adapter = coinListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}