package com.example.kts_android_09_2021.presentation.fragments.profile_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentUserProfileBinding
import com.example.kts_android_09_2021.presentation.fragments.main_fragment.MainFragmentPagesAdapter
import com.example.kts_android_09_2021.presentation.fragments.profile_fragment.liked_photos_fragment.LikedPhotosFragment
import com.example.kts_android_09_2021.presentation.fragments.profile_fragment.user_photos_fragment.UserPhotosFragment
import com.example.kts_android_09_2021.domain.enteties.profile.AuthorizedUser
import com.example.kts_android_09_2021.domain.common.logExceptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private val binding: FragmentUserProfileBinding by viewBinding(FragmentUserProfileBinding::bind)
    private val viewModel: ProfileFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startListenFlow()
        setLogOutButton()
    }

    private fun startListenFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.connectionObserver
                        .catch { logExceptions(it) }
                        .collect { isConnected ->
                            binding.tvUserFragConnectionMessage.isVisible = !isConnected
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
                        .collect { authorizedUser ->
                            if (authorizedUser != null) {
                                initMainFields(authorizedUser)
                                binding.vpProfile.adapter = MainFragmentPagesAdapter(
                                    this@ProfileFragment,
                                    listOf(UserPhotosFragment(), LikedPhotosFragment())
                                )
                                attachTableLayout()
                            }
                        }
                }
            }
        }
    }

    private fun attachTableLayout() {
        TabLayoutMediator(binding.tlProfile, binding.vpProfile) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.profile_table_layout_photos)
                1 -> tab.text = getString(R.string.profile_table_layout_likes)
                2 -> tab.text = getString(R.string.profile_table_layout_collections)
            }
        }.attach()
    }

    private fun initMainFields(user: AuthorizedUser) {
        with(binding) {
            tvProfileUserFullName.text = "${user.firstName} ${user.lastName}"
            tvProfileUsername.text =
                "${getString(R.string.mail_symbol_for_username)}${user.username}"
            tvProfileStatus.text =
                user.bio ?: "${getString(R.string.default_bio)} ${user.firstName}."
        }

        Glide.with(requireContext())
            .load(user.avatarPhoto?.medium)
            .into(binding.profileUserPhoto)
    }

    private fun setLogOutButton(){
        binding.profileFragmentIvLogOut.setOnClickListener {
            viewModel.logOut()
        }
    }
}