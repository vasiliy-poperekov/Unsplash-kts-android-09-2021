package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentEditorialBinding
import com.example.kts_android_09_2021.utils.PaginationScrollListener
import com.example.kts_android_09_2021.utils.autoCleared

class EditorialFragment : Fragment(R.layout.fragment_editorial) {
    private val binding: FragmentEditorialBinding by viewBinding(FragmentEditorialBinding::bind)
    private var editorialAdapter: EditorialFragmentAdapter by autoCleared()
    private val viewModel: EditorialViewModel by viewModels()
    private lateinit var paginationScrollListener: PaginationScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()

        viewModel.savedItemsInfoLiveData.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.rvEditorial.visibility = View.GONE
                binding.tvEditorialSorryMessage.visibility = View.VISIBLE
            } else {
                binding.tvEditorialSorryMessage.visibility = View.GONE
                binding.rvEditorial.visibility = View.VISIBLE
                editorialAdapter.items = it
                paginationScrollListener.isLoading = true
            }
        })

        viewModel.limitLiveData.observe(viewLifecycleOwner, {
            paginationScrollListener.isLastPage = true
            Toast.makeText(requireContext(), "Limit was reached", Toast.LENGTH_SHORT).show()
        })

        viewModel.changingInItemsLiveData.observe(viewLifecycleOwner, {
            editorialAdapter.notifyItemChanged(it[0], listOf(it[1]))
        })
    }

    private fun initList() {
        editorialAdapter = EditorialFragmentAdapter { viewModel.likePhoto(it) }
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