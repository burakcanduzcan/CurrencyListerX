package com.burakcanduzcan.currencylisterx.ui.portfolio

import androidx.fragment.app.viewModels
import com.burakcanduzcan.currencylisterx.core.BaseFragment
import com.burakcanduzcan.currencylisterx.databinding.FragmentPortfolioBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PortfolioFragment :
    BaseFragment<FragmentPortfolioBinding>(FragmentPortfolioBinding::inflate) {

    override val viewModel: PortfolioViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        Timber.i("PortfolioFragment onResume")
    }

    override fun initializeViews() {
        Timber.i("PortfolioFragment onCreateView")
    }
}