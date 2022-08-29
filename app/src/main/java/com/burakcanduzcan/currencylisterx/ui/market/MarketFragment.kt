package com.burakcanduzcan.currencylisterx.ui.market

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.core.BaseFragment
import com.burakcanduzcan.currencylisterx.databinding.FragmentMarketBinding
import com.burakcanduzcan.currencylisterx.model.CryptoCoinUiModel
import com.burakcanduzcan.currencylisterx.util.Globals
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MarketFragment : BaseFragment<FragmentMarketBinding>(FragmentMarketBinding::inflate) {

    override val viewModel: MarketViewModel by viewModels()
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onResume() {
        super.onResume()
        Timber.i("MarketFragment onResume")
        autoSelectToggleButtonGroup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("MarketFragment onDestroyView")
        Timber.i("MarketFragment last selected currency is ${Globals.CURRENCY}")
    }

    override fun initializeViews() {
        Timber.i("MarketFragment onCreateView")
        //toggle group
        binding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnToggle1 -> {
                        toggleButtonGroupSelection("USD")
                    }
                    R.id.btnToggle2 -> {
                        toggleButtonGroupSelection("EUR")

                    }
                    R.id.btnToggle3 -> {
                        toggleButtonGroupSelection("TRY")
                    }
                }
            }
        }
        //recyclerView
        binding.rvTodos.apply {
            coinListAdapter = CoinListAdapter(requireContext(), ::onCoinClick)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun resetRecyclerView() {
        binding.rvTodos.adapter = null
        binding.rvTodos.layoutManager = null
        binding.rvTodos.adapter = coinListAdapter
        binding.rvTodos.layoutManager = LinearLayoutManager(requireContext())
        coinListAdapter.notifyDataSetChanged()
    }

    private fun toggleButtonGroupSelection(newCurrency: String) {
        Timber.i("MarketFragment Toggle group: selected currency $newCurrency")
        //change current currency to selected currency
        Globals.CURRENCY = newCurrency
        Timber.i("MarketFragment Global CURRENCY: ${Globals.CURRENCY}")
        //re-fetch data from api with updated currency parameter
        viewModel.refresh()
        resetRecyclerView()
    }

    private fun autoSelectToggleButtonGroup() {
        when (Globals.CURRENCY) {
            "USD" -> {
                binding.toggleButtonGroup.check(R.id.btnToggle1)
            }
            "EUR" -> {
                binding.toggleButtonGroup.check(R.id.btnToggle2)
            }
            "TRY" -> {
                binding.toggleButtonGroup.check(R.id.btnToggle3)
            }
        }
    }

    private fun onCoinClick(coin: CryptoCoinUiModel) {
        this.findNavController().navigate(MarketFragmentDirections.actionMarketFragmentToDetailFragment(coin))
    }
}