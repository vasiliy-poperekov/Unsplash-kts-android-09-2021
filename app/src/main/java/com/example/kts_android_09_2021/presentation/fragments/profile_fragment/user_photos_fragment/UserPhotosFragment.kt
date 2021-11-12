package com.example.kts_android_09_2021.presentation.fragments.profile_fragment.user_photos_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentUserPhotosBinding
import com.example.kts_android_09_2021.domain.items.ItemLoading
import com.example.kts_android_09_2021.presentation.utils.PaginationScrollListener
import com.example.kts_android_09_2021.presentation.utils.autoCleared
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPhotosFragment : Fragment(R.layout.fragment_user_photos) {

    private val binding: FragmentUserPhotosBinding by viewBinding(FragmentUserPhotosBinding::bind)
    private var userPhotosAdapter: UserPhotosFragmentAdapter by autoCleared()
    private val viewModel: UserPhotosFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRV()
        bindViewModel()
        setRefreshing()
    }

    private fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.photosListObserver
                        .collect { userPhotosList ->
                            if (userPhotosList != null) {
                                when {
                                    userPhotosList.isEmpty() -> {
                                        showMessageText(R.string.you_didnt_post_photos)
                                    }
                                    else -> {
                                        binding.tvEmptyPhotosList.visibility = View.GONE
                                        userPhotosAdapter.items = userPhotosList
                                    }
                                }
                            }

                        }
                }
                launch {
                    viewModel.errorsObserver
                        .collect { exception ->
                            if (exception != null) showMessageText(R.string.wrong_text_message)
                        }
                }
            }

        }

    }

    private fun setRefreshing() {
        binding.userPhotosFragmentRefreshLayout.setOnRefreshListener {
            initRV()
            viewModel.refreshPhotosList()
            binding.userPhotosFragmentRefreshLayout.isRefreshing = false
        }
    }

    private fun initRV() {
        userPhotosAdapter = UserPhotosFragmentAdapter()
        with(binding.rvUserPhotos) {
            adapter = userPhotosAdapter
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addOnScrollListener(
                PaginationScrollListener(
                    layoutManager = layoutManager as LinearLayoutManager,
                    pasteNewItems = { load ->
                        viewModel.getPhotosList { load() }
                    }
                )
            )
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun showMessageText(messageId: Int) {
        with(binding.tvEmptyPhotosList) {
            visibility = View.VISIBLE
            text = getString(messageId)
        }
    }
}