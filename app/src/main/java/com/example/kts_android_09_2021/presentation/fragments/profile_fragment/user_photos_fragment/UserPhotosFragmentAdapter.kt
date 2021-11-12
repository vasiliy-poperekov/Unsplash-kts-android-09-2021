package com.example.kts_android_09_2021.presentation.fragments.profile_fragment.user_photos_fragment

import com.example.kts_android_09_2021.presentation.fragments.editorial_fragment.delegates.ItemLoadingDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UserPhotosFragmentAdapter :
    AsyncListDifferDelegationAdapter<Any>(ItemUserPhotoDiffUtilCallback()) {
    init {
        delegatesManager
            .addDelegate(ItemUserPhotosDelegate())
            .addDelegate(ItemLoadingDelegate())
    }
}