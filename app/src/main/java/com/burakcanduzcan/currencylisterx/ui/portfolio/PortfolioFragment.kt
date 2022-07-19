package com.burakcanduzcan.currencylisterx.ui.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.burakcanduzcan.currencylisterx.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPortfolioBinding.inflate(inflater)
        return binding.root
    }
}