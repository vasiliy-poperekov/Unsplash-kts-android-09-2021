package com.example.kts_android_09_2021.fragments.main_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentMainBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.EditorialFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpMainFrag.adapter = MainFragmentPagesAdapter(
            this,
            listOf(EditorialFragment())
        )

        TabLayoutMediator(binding.tlMainFrag, binding.vpMainFrag) { tab, position ->
            when (position) {
                0 -> tab.text = "Editorial"
            }
        }.attach()

        binding.mainFragButtonToUserProfile.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
        }
    }
}