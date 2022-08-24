package com.burakcanduzcan.currencylisterx.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.burakcanduzcan.currencylisterx.R
import com.burakcanduzcan.currencylisterx.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //force dark mode
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationComponent()
    }

    private fun setupNavigationComponent() {
        //getting navController from fragmentContainerView, registered as NavHostFragment
        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!
            .findNavController()
        //setting up bottomNavigationView with Navigation Component
        binding.bottomNavView.setupWithNavController(navController)
    }
}