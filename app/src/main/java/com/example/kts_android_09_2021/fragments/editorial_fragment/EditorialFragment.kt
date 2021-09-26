package com.example.kts_android_09_2021.fragments.editorial_fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.FragmentEditorialBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemLoading
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithImage
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithoutImage
import com.example.kts_android_09_2021.utils.PaginationScrollListener
import com.example.kts_android_09_2021.utils.autoCleared
import kotlin.random.Random

class EditorialFragment : Fragment(R.layout.fragment_editorial) {
    private val binding: FragmentEditorialBinding by viewBinding(FragmentEditorialBinding::bind)
    private var editorialAdapter: EditorialFragmentAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        loadItemsInRecycler()
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
                    pasteNewItems = ::loadItemsInRecycler,
                    visibilityThreshold = 0
                )
            )

            addItemDecoration(DividerItemDecoration(context, orientation))
            setHasFixedSize(true)
        }
    }

    private fun getNewItems() = List(20) {
        when ((1..2).random()) {
            1 -> ItemWithImage(
                Random.nextInt(),
                "Simple title",
                "Somebody"
            )
            2 -> ItemWithoutImage(
                Random.nextInt(),
                "Simple title",
                "Somebody",
                (0..100).random()
            )
            else -> error("Wrong random number")
        }
    }

    private fun loadItemsInRecycler() {
        val newItems = editorialAdapter.items.toMutableList().apply {
            if (lastOrNull() is ItemLoading) removeLastOrNull()
        } + getNewItems() + ItemLoading()
        editorialAdapter.items = newItems
        Log.e("Pagination", newItems.size.toString())
    }
}