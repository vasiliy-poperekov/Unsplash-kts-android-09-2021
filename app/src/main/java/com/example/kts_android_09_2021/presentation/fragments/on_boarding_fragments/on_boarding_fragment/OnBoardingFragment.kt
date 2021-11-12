package com.example.kts_android_09_2021.presentation.fragments.on_boarding_fragments.on_boarding_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentOnBoardingBinding
import com.example.kts_android_09_2021.domain.items.ScreenItem
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {
    private val binding: FragmentOnBoardingBinding by viewBinding(FragmentOnBoardingBinding::bind)
    private val viewModel: OnBoardingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.enterObserver.collect {
                    if (it == true) {
                        findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
                    }
                }
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViewModel()

        viewModel.saveItems(createViewPagerItems())

        binding.skipOnBoarding.setOnClickListener {
            viewModel.enterWasDone()
        }
    }

    private fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedItemsObserver.collect { screensList ->
                    if (screensList != null) {
                        binding.viewPagerOnBoarding.adapter =
                            OnBoardingViewPagerAdapter(this@OnBoardingFragment, screensList)

                        TabLayoutMediator(
                            binding.tabLayoutOnBoarding,
                            binding.viewPagerOnBoarding
                        ) { _, _ ->

                        }.attach()
                    }
                }
            }
        }
    }

    private fun createViewPagerItems() =
        with(requireContext()) {
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
}