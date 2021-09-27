package com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.example.kts_android_09_2021.databinding.ItemEditorialTestWithoutImageBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemWithoutImage

class ItemWithoutImageViewHolder(
    private val binding: ItemEditorialTestWithoutImageBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val authorString = "Author: "

    fun bind(item: ItemWithoutImage) {
        binding.tvWithoutTitle.text = item.title
        binding.tvWithoutAuthor.text = authorString.plus(item.author)
        binding.tvWithoutLikeCounter.text = item.likeCounter.toString()
        binding.ivWithoutLikeButton.setOnClickListener {
            item.likeCounter++
            binding.tvWithoutLikeCounter.text = item.likeCounter.toString()
        }
    }
}