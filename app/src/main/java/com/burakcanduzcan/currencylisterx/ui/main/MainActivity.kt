package com.burakcanduzcan.currencylisterx.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.burakcanduzcan.currencylisterx.BuildConfig
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.databinding.ActivityMainBinding
import com.burakcanduzcan.currencylisterx.ui.market.MarketFragment
import com.burakcanduzcan.currencylisterx.ui.portfolio.PortfolioFragment
import com.burakcanduzcan.currencylisterx.ui.updates.UpdatesFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimber()
        initializeViews()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeViews() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            //todo: fragment transition animations
            when (item.itemId) {
                R.id.page1 -> {
                    Timber.i("Item1 selection")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, MarketFragment()).commit()
                    true
                }
                R.id.page2 -> {
                    Timber.i("Item2 selection")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, PortfolioFragment()).commit()
                    true
                }
                R.id.page3 -> {
                    Timber.i("Item3 selection")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, UpdatesFragment()).commit()
                    true
                }
                else -> {
                    Timber.e("Invalid selection")
                    false
                }
            }
        }

        //manual navigation item selection:
        // might be required for certain triggers but triggers only
        // such as fragment transaction
        binding.bottomNavView.selectedItemId = R.id.page1
    }
}