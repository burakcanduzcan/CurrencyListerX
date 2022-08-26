package com.burakcanduzcan.currencylisterx.ui.market

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.databinding.FragmentMarketBinding
import com.burakcanduzcan.currencylisterx.util.Globals
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarketViewModel by viewModels()
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMarketBinding.inflate(inflater)

        Timber.i("MarketFragment onCreateView")
        initializeViews()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Timber.i("MarketFragment onResume")
        autoSelectToggleButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        //toggle group
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnToggle1 -> {
                        toggleSelection("USD")
                    }
                    R.id.btnToggle2 -> {
                        toggleSelection("EUR")

                    }
                    R.id.btnToggle3 -> {
                        toggleSelection("TRY")
                    }
                }
            }
        }
        //recyclerView
        binding.rvTodos.apply {
            coinListAdapter = CoinListAdapter(requireContext())
            adapter = coinListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        //observe
        viewModel.coinListLiveData.observe(viewLifecycleOwner) {
            coinListAdapter.submitList(it)
            //binding.rvTodos.adapter = coinListAdapter
            updateProgressBar()
            binding.srLayout.isRefreshing = false
        }
        //swipe refresh
        binding.srLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun updateProgressBar() {
        if (viewModel.isDataSet) {
            binding.progressBar.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun toggleSelection(newCurrency: String) {
        Timber.i("Toggle group: selected currency $newCurrency")
        //change current currency to selected currency
        Globals.CURRENCY = newCurrency
        Timber.i("Global CURRENCY: ${Globals.CURRENCY}")
        //re-fetch data from api with updated currency parameter
        viewModel.refresh()
        resetRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetRecyclerView() {
        binding.rvTodos.adapter = null
        binding.rvTodos.layoutManager = null
        binding.rvTodos.adapter = coinListAdapter
        binding.rvTodos.layoutManager = LinearLayoutManager(requireContext())
        coinListAdapter.notifyDataSetChanged()
    }

    private fun autoSelectToggleButton() {
        when (Globals.CURRENCY) {
            "USD" -> {
                binding.toggleButton.check(R.id.btnToggle1)
            }
            "EUR" -> {
                binding.toggleButton.check(R.id.btnToggle2)
            }
            "TRY" -> {
                binding.toggleButton.check(R.id.btnToggle3)
            }
        }
    }
}