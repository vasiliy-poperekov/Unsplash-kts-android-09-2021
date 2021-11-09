package com.example.kts_android_09_2021.fragments.main_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentMainBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.EditorialFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpMainFrag.adapter = MainFragmentPagesAdapter(
            this,
            listOf(EditorialFragment())
        )

        TabLayoutMediator(binding.tlMainFrag, binding.vpMainFrag) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.main_fragment_editorial_label)
            }
        }.attach()

        binding.mainFragButtonToUserProfile.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.networkingObserver
                    .collect { isConnected ->
                        binding.tvMainFragConnectionMessage.isVisible = !isConnected
                    }
            }
        }
    }
}