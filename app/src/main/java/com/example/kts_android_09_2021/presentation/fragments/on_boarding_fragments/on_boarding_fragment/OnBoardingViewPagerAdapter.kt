package com.example.kts_android_09_2021.presentation.fragments.on_boarding_fragments.on_boarding_fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kts_android_09_2021.presentation.fragments.on_boarding_fragments.screen_fragment.OnBoardingFragmentScreen
import com.example.kts_android_09_2021.domain.items.ScreenItem

class OnBoardingViewPagerAdapter(
    fragment: Fragment,
    private val screenItemsList: List<ScreenItem>,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = screenItemsList.size

    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragmentScreen().apply {
            screenItem = screenItemsList[position]
        }
    }
}