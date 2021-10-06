package com.example.kts_android_09_2021.fragments.editorial_fragment

import androidx.recyclerview.widget.RecyclerView
import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemEditorialDelegate
import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemLoadingDelegate
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders.ItemEditorialViewHolder
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class EditorialFragmentAdapter(
    private val likePhoto: (ItemEditorial) -> Unit
) : AsyncListDifferDelegationAdapter<Any>(DiffUtilItemsCallback()) {

    init {
        delegatesManager
            .addDelegate(ItemLoadingDelegate())
            .addDelegate(ItemEditorialDelegate { likePhoto(it) })
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        if (payloads.isNotEmpty()) (holder as ItemEditorialViewHolder).setProgress(payloads[0].toString()[1].digitToInt())
        else super.onBindViewHolder(holder, position, payloads)
    }
}