package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()

        viewModel.savedItemsInfoLiveData.observe(viewLifecycleOwner, {
            editorialAdapter.items = it
        })
    }

    private fun initList() {
        editorialAdapter = EditorialFragmentAdapter()
        with(binding.rvEditorial) {
            val orientation = RecyclerView.VERTICAL
            adapter = editorialAdapter
            layoutManager = LinearLayoutManager(context, orientation, false)

            addOnScrollListener(
                PaginationScrollListener(
                    layoutManager = layoutManager as LinearLayoutManager,
                    pasteNewItems = { viewModel.loadItemsInRecycler() },
                    visibilityThreshold = 1
                )
            )

            addItemDecoration(DividerItemDecoration(context, orientation))
            setHasFixedSize(true)
        }
    }
}