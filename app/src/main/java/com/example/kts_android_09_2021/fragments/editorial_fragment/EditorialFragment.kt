package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentEditorialBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.EditorialViewModel.Companion.DEFAULT_PAGE_NUMBER
import com.example.kts_android_09_2021.utils.PaginationScrollListener
import com.example.kts_android_09_2021.utils.autoCleared
import com.example.kts_android_09_2021.utils.logExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class EditorialFragment : Fragment(R.layout.fragment_editorial) {
    private val binding: FragmentEditorialBinding by viewBinding(FragmentEditorialBinding::bind)
    private var editorialAdapter: EditorialFragmentAdapter by autoCleared()
    private val viewModel: EditorialViewModel by viewModels()
    private lateinit var paginationScrollListener: PaginationScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.savedItemsInfoObserver
                        .catch { logExceptions(it) }
                        .debounce(1000)
                        .flowOn(Dispatchers.Main)
                        .collect {
                            if (it.isNullOrEmpty()) {
                                binding.rvEditorial.visibility = View.GONE
                                binding.tvEditorialSorryMessage.visibility = View.VISIBLE
                            } else {
                                binding.tvEditorialSorryMessage.visibility = View.GONE
                                binding.rvEditorial.visibility = View.VISIBLE
                                editorialAdapter.items = it
                                paginationScrollListener.isLoading = true
                            }
                        }
                }

                launch {
                    viewModel.limitObserver
                        .catch { logExceptions(it) }
                        .flowOn(Dispatchers.Main)
                        .collect {
                            paginationScrollListener.isLastPage = it
                            if (it) Toast.makeText(
                                requireContext(),
                                getString(R.string.limit_was_reached),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

            }
        }

        viewModel.changingInItemsLiveData.observe(viewLifecycleOwner, {
            editorialAdapter.notifyItemChanged(it, Unit)
        })

        binding.fragmentEditorialRefreshLayout.setOnRefreshListener {
            viewModel.getNewItems(DEFAULT_PAGE_NUMBER)
            binding.fragmentEditorialRefreshLayout.isRefreshing = false
        }
    }

    private fun initList() {
        editorialAdapter =
            EditorialFragmentAdapter({ viewModel.likePhoto(it) }, { viewModel.unLikePhoto(it) })
        with(binding.rvEditorial) {
            val orientation = RecyclerView.VERTICAL
            adapter = editorialAdapter
            layoutManager = LinearLayoutManager(context, orientation, false)
            paginationScrollListener = PaginationScrollListener(
                layoutManager = layoutManager as LinearLayoutManager,
                pasteNewItems = { viewModel.getNewItems(it) },
            )

            addOnScrollListener(
                paginationScrollListener
            )

            addItemDecoration(DividerItemDecoration(context, orientation))
            setHasFixedSize(true)
        }
    }
}