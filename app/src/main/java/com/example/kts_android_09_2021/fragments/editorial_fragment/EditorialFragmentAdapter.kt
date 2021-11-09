package com.example.kts_android_09_2021.fragments.editorial_fragment

import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemEditorialDelegate
import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemLoadingDelegate
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class EditorialFragmentAdapter(
    likePhoto: (ItemEditorial) -> Unit,
    unLikePhoto: (ItemEditorial) -> Unit
) : AsyncListDifferDelegationAdapter<Any>(EditorialDiffUtilItemsCallback()) {

    init {
        delegatesManager
            .addDelegate(ItemLoadingDelegate())
            .addDelegate(ItemEditorialDelegate(likePhoto, unLikePhoto))
    }
}