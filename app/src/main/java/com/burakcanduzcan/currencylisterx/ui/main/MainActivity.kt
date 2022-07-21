package com.burakcanduzcan.currencylisterx.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.burakcanduzcan.currencylisterx.BuildConfig
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTimber()
        setupNavigationComponent()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupNavigationComponent() {
        //getting navController from fragmentContainerView, registered as NavHostFragment
        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!
            .findNavController()
        //setting up bottomNavigationView with Navigation Component
        binding.bottomNavView.setupWithNavController(navController)
    }
}