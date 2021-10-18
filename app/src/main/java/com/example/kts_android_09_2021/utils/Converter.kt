package com.example.kts_android_09_2021.utils

import com.example.kts_android_09_2021.db.enteties.EditorialEntity
import com.example.kts_android_09_2021.fragments.editorial_fragment.items.ItemEditorial
import com.example.kts_android_09_2021.network.entities.photos_list.Photo

object Converter {
    fun convertEditorialItemInEntity(itemEditorial: ItemEditorial?): EditorialEntity {
        return EditorialEntity(
            id = itemEditorial!!.id,
            userName = itemEditorial.userName,
            avatarUrl = itemEditorial.avatarUrl,
            imageUrl = itemEditorial.imageUrl,
            likes = itemEditorial.likes,
            likedByUser = itemEditorial.likedByUser
        )
    }

    fun convertEditorialEntityInItem(editorialEntity: EditorialEntity): ItemEditorial {
        return ItemEditorial(
            id = editorialEntity.id,
            userName = editorialEntity.userName,
            avatarUrl = editorialEntity.avatarUrl,
            imageUrl = editorialEntity.imageUrl,
            likes = editorialEntity.likes,
            likedByUser = editorialEntity.likedByUser
        )
    }

    fun convertPhotoInItem(photo: Photo): ItemEditorial {
        return ItemEditorial(
            id = photo.id,
            userName = photo.user.userName,
            imageUrl = photo.urls.full,
            avatarUrl = photo.user.profileImage.medium!!,
            likes = photo.likes,
            likedByUser = photo.likedByUser
        )
    }
}