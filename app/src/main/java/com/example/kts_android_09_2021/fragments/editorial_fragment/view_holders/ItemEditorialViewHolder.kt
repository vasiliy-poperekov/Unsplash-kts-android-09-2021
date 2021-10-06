package com.example.kts_android_09_2021.fragments.editorial_fragment.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kts_android_09_2021.R
import com.example.kts_android_09_2021.databinding.ItemEditorialBinding
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import kotlinx.coroutines.*

class ItemEditorialViewHolder(
    private val binding: ItemEditorialBinding,
    private val likePhoto: (ItemEditorial) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemEditorial) {
        binding.tvUserName.text = item.userName

        if (item.likedByUser) {
            binding.ivButtonLike.setImageResource(R.drawable.ic_baseline_favorite_24)
            binding.ivButtonLike.setOnClickListener(null)
        } else {
            binding.ivButtonLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            binding.ivButtonLike.setOnClickListener {
                likePhoto(item)
            }
        }

        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivItemEditorial)

        Glide.with(binding.root)
            .load(item.avatarUrl)
            .into(binding.civAvatar)
    }

    fun setProgress(likesNumber: Int) {
        binding.ivButtonLike.setImageResource(R.drawable.ic_baseline_favorite_24)
        binding.ivButtonLike.setOnClickListener(null)
        binding.editorialItemTvLikeCounter.text = likesNumber.toString()
        GlobalScope.launch {
            binding.editorialItemTvLikeCounter.visibility = View.VISIBLE
            delay(3000)
            binding.editorialItemTvLikeCounter.visibility = View.INVISIBLE
            cancel()
        }
    }

}