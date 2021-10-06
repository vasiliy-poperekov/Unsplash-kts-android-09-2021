package com.example.kts_android_09_2021.fragments.profile_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentUserProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val binding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUserLiveData.observe(viewLifecycleOwner, {
            binding.profileUserInfo.text = it.toString()
            Glide.with(requireContext())
                .load(it.profileImage?.medium)
                .into(binding.profileUserPhoto)
        })
    }

}