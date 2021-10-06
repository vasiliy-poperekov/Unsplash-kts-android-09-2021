package com.example.kts_android_09_2021.fragments.on_boarding_fragments.on_boarding_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentOnBoardingBinding
import com.example.kts_android_09_2021.fragments.on_boarding_fragments.screen_fragment.ScreenItem
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val binding: FragmentOnBoardingBinding by viewBinding(FragmentOnBoardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPagerOnBoarding.adapter =
            OnBoardingViewPagerAdapter(this, createViewPagerItems())

        TabLayoutMediator(
            binding.tabLayoutOnBoarding,
            binding.viewPagerOnBoarding
        ) { _, _ -> }.attach()

        binding.skipOnBoarding.setOnClickListener {
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
        }
    }

    private fun createViewPagerItems() =
        listOf(
            ScreenItem(
                getString(R.string.first_on_boarding_screen_title),
                getString(R.string.first_on_boarding_screen_subtitle),
                R.drawable.default_on_boarding_image
            ),
            ScreenItem(
                getString(R.string.second_on_boarding_screen_title),
                getString(R.string.second_on_boarding_screen_subtitle),
                R.drawable.default_image
            ),
            ScreenItem(
                getString(R.string.third_on_boarding_screen_title),
                getString(R.string.third_on_boarding_screen_subtitle),
                R.drawable.default_on_boarding_image
            )
        )
}