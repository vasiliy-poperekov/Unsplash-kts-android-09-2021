package com.example.kts_android_09_2021.fragments.editorial_fragment

import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemLoadingDelegate
import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemWithImageDelegate
import com.example.kts_android_09_2021.fragments.editorial_fragment.delegates.ItemWithoutImageDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class EditorialFragmentAdapter : AsyncListDifferDelegationAdapter<Any>(DiffUtilItemsCallback()) {

    init {
        delegatesManager
            .addDelegate(ItemWithImageDelegate())
            .addDelegate(ItemWithoutImageDelegate())
            .addDelegate(ItemLoadingDelegate())
    }
}