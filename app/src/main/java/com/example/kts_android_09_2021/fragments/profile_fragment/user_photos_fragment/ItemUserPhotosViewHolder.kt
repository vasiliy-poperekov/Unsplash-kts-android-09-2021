package com.example.kts_android_09_2021.fragments.profile_fragment.user_photos_fragment

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kts_android_09_2021.databinding.ItemProfilePhotoBinding

class ItemUserPhotosViewHolder(
    private val binding: ItemProfilePhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemUserPhoto) {
        binding.tvUserName.text = item.userName

        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivItemEditorial)

        Glide.with(binding.root)
            .load(item.avatarUrl)
            .into(binding.civAvatar)
    }
}