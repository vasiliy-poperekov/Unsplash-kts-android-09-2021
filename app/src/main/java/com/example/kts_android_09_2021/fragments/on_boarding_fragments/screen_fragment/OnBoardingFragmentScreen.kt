package com.example.kts_android_09_2021.fragments.on_boarding_fragments.screen_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentOnBoardingScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragmentScreen : Fragment(R.layout.fragment_on_boarding_screen) {
    private val binding: FragmentOnBoardingScreenBinding by viewBinding(
        FragmentOnBoardingScreenBinding::bind
    )
    lateinit var screenItem: ScreenItem
    private val viewModel: OnBoardingFragmentScreenViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (::screenItem.isInitialized) viewModel.screenItem = screenItem
        binding.tvTitleScreen.text = viewModel.screenItem.title
        binding.tvSubtitleScreen.text = viewModel.screenItem.subtitle
        binding.ivScreen.setImageResource(viewModel.screenItem.imageInt)
    }
}