package com.seoleo.fintechlab2023

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.seoleo.fintechlab2023.databinding.ActivityMainBinding
import com.seoleo.fintechlab2023.ui.model.FragmentType
import com.seoleo.fintechlab2023.ui.screens.favourites.FavouritesFragment
import com.seoleo.fintechlab2023.ui.screens.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var type: FragmentType


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if (savedInstanceState == null) {
            showFragment(FragmentType.Populars)
        }

        listeners()

        supportFragmentManager.setFragmentResultListener("hide", this) { _, result ->
            val hide = result.getBoolean("hide", false)
            binding.buttonContent.isGone = hide
        }

        supportFragmentManager.addOnBackStackChangedListener {
            binding.buttonContent.isGone = supportFragmentManager.backStackEntryCount == 1
        }
    }

    private fun listeners() {
        binding.favourites.setOnClickListener {
            if (::type.isInitialized) {
                if (type == FragmentType.Favourites) {
                    return@setOnClickListener
                }
            }
            showFragment(FragmentType.Favourites)
        }

        binding.populars.setOnClickListener {
            if (::type.isInitialized) {
                if (type == FragmentType.Populars) {
                    return@setOnClickListener
                }
            }
            showFragment(FragmentType.Populars)
        }
    }

    private fun showFragment(fragmentType: FragmentType) {
        val activeButtonBackgroundColor = R.color.active_button_background_color
        val buttonBackgroundColor = R.color.button_background_color
        val white = R.color.white
        val fragment = when (fragmentType) {
            FragmentType.Populars -> {
                binding.populars.backgroundTintList = AppCompatResources.getColorStateList(this, buttonBackgroundColor)
                binding.populars.setTextColor(ContextCompat.getColor(applicationContext, activeButtonBackgroundColor))
                binding.favourites.backgroundTintList = AppCompatResources.getColorStateList(this, activeButtonBackgroundColor)
                binding.favourites.setTextColor(ContextCompat.getColor(applicationContext, white))
                MainFragment()
            }
            FragmentType.Favourites -> {
                binding.populars.backgroundTintList = AppCompatResources.getColorStateList(this, activeButtonBackgroundColor)
                binding.populars.setTextColor(ContextCompat.getColor(applicationContext, white))
                binding.favourites.backgroundTintList = AppCompatResources.getColorStateList(this, buttonBackgroundColor)
                binding.favourites.setTextColor(ContextCompat.getColor(applicationContext, activeButtonBackgroundColor))
                FavouritesFragment()
            }
        }
        type = fragmentType
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}