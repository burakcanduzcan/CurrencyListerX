package com.burakcanduzcan.currencylisterx.ui.updates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.burakcanduzcan.currencylisterx.databinding.FragmentUpdatesBinding

class UpdatesFragment : Fragment() {

    private lateinit var binding: FragmentUpdatesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUpdatesBinding.inflate(inflater)

        return binding.root
    }

}