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
    private val likePhoto: (ItemEditorial) -> Unit,
    private val unLikePhoto: (ItemEditorial) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentItem: ItemEditorial

    fun bind(item: ItemEditorial) {
        currentItem = item

        setLikeButtonActions()

        binding.tvUserName.text = currentItem.userName

        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivItemEditorial)

        Glide.with(binding.root)
            .load(item.avatarUrl)
            .into(binding.civAvatar)
    }

    fun photoWasLiked() {
        if (!currentItem.likedByUser) {
            currentItem.likedByUser = false
            currentItem.likes = currentItem.likes - 1
            setLikeButtonActions()
        } else {
            currentItem.likedByUser = true
            currentItem.likes = currentItem.likes + 1
            setLikeButtonActions()
            binding.editorialItemTvLikeCounter.text = currentItem.likes.toString()
            CoroutineScope(Dispatchers.Main).launch {
                binding.editorialItemTvLikeCounter.visibility = View.VISIBLE
                delay(3000)
                binding.editorialItemTvLikeCounter.visibility = View.INVISIBLE
            }
        }
    }

    private fun setLikeButtonActions() {
        with(binding.ivButtonLike) {
            if (currentItem.likedByUser) {
                setImageResource(R.drawable.ic_baseline_favorite_24)
                setOnClickListener {
                    binding.editorialItemTvLikeCounter.visibility = View.INVISIBLE
                    unLikePhoto(currentItem)
                }
            } else {
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
                setOnClickListener {
                    likePhoto(currentItem)
                }
            }
        }
    }
}