package com.burakcanduzcan.currencylisterx.ui.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.burakcanduzcan.currencylisterx.databinding.FragmentPortfolioBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PortfolioFragment : Fragment() {

    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Timber.i("PortfolioFragment onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}