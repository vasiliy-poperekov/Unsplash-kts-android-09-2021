package com.example.kts_android_09_2021.presentation.fragments.editorial_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentEditorialBinding
import com.example.kts_android_09_2021.presentation.utils.PaginationScrollListener
import com.example.kts_android_09_2021.presentation.utils.autoCleared
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditorialFragment : Fragment(R.layout.fragment_editorial) {
    private val binding: FragmentEditorialBinding by viewBinding(FragmentEditorialBinding::bind)
    private var editorialAdapter: EditorialFragmentAdapter by autoCleared()
    private val viewModel: EditorialViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        bindViewModel()
        setRefreshing()
    }

    private fun setRefreshing() {
        binding.fragmentEditorialRefreshLayout.setOnRefreshListener {
            initList()
            viewModel.refreshList()
            binding.fragmentEditorialRefreshLayout.isRefreshing = false
        }
    }

    private fun bindViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(viewModel) {
                    with(binding) {
                        launch {
                            savedItemsInfoObserver
                                .collect { photosList ->
                                    if (photosList != null && photosList.isEmpty()) {
                                        showMessageText(R.string.fragment_editorial_sorry_message)
                                    } else if (photosList != null) {
                                        tvEditorialSorryMessage.visibility = View.GONE
                                        rvEditorial.visibility = View.VISIBLE
                                        editorialAdapter.items = photosList
                                    }
                                }
                        }
                        launch {
                            errorsObserver.collect { error ->
                                if (error != null) {
                                    showMessageText(R.string.wrong_text_message)
                                }
                            }
                        }
                    }
                    launch {
                        changingInItemsObserver.collect { itemsIndex ->
                            editorialAdapter.notifyItemChanged(itemsIndex, Unit)
                        }
                    }
                }

            }
        }
    }

    private fun showMessageText(resId: Int) {
        binding.tvEditorialSorryMessage.text = getString(resId)
        binding.rvEditorial.visibility = View.GONE
        binding.tvEditorialSorryMessage.visibility = View.VISIBLE
    }

    private fun initList() {
        editorialAdapter =
            EditorialFragmentAdapter({ viewModel.likePhoto(it) }, { viewModel.unLikePhoto(it) })
        with(binding.rvEditorial) {
            val orientation = RecyclerView.VERTICAL
            adapter = editorialAdapter
            layoutManager = LinearLayoutManager(context, orientation, false)

            addOnScrollListener(
                PaginationScrollListener(
                    layoutManager = layoutManager as LinearLayoutManager,
                    pasteNewItems = { load ->
                        viewModel.getNewItems(load)
                    }
                )
            )
        }
    }
}