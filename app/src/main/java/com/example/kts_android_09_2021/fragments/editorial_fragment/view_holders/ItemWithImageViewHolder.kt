package com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kts_android_09_2021.databinding.ItemEditorialTestWithImageBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithImage

class ItemWithImageViewHolder(
    private val binding: ItemEditorialTestWithImageBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemWithImage) {
        binding.tvTitle.text = item.title
        binding.tvAuthor.text = binding.tvAuthor.text.toString().plus(item.author)
    }
}