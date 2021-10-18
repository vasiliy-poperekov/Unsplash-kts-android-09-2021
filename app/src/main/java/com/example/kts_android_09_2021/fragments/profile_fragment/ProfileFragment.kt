package com.example.kts_android_09_2021.fragments.profile_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentUserProfileBinding
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val binding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startListenFlow()

        binding.bvProfileFragmentLogOut.setOnClickListener {
            viewModel.logOut()
        }
    }

    private fun startListenFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.connectionObserver
                        .catch { logExceptions(it) }
                        .collect { isConnected ->
                            if (isConnected) {
                                binding.tvUserFragConnectionMessage.visibility = View.GONE
                            } else {
                                binding.tvUserFragConnectionMessage.visibility = View.VISIBLE
                            }
                        }
                }
                launch {
                    viewModel.tokenObserver
                        .catch { logExceptions(it) }
                        .collect {
                            if (it!!.isEmpty()) {
                                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                            }
                        }
                }
                launch {
                    viewModel.currentUserObserver
                        .catch { logExceptions(it) }
                        .collect {
                            if (it != null) {
                                binding.profileUserInfo.text = it.toString()
                                Glide.with(requireContext())
                                    .load(it.profileImage?.medium)
                                    .into(binding.profileUserPhoto)
                            }
                        }
                }
            }
        }
    }
}